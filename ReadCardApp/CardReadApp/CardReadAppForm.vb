﻿Imports System.Diagnostics
Imports System.IO

Public Class CardReadAppForm
    Private dbPath As String = ""
    Private batPath As String = ""

    Private Sub CardReadAppForm_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.ActiveControl = Me.BrowseDatabaseBtn
    End Sub

    Private Sub ReadCardBtn_Click(sender As Object, e As EventArgs) Handles ReadCardBtn.Click
        Try
            ' Clear form before starts reading card
            ClearForm()

            ' Start running batch file
            Dim myProcess As New Process
            myProcess.StartInfo.FileName = batPath
            myProcess.StartInfo.Arguments = batPath & "..\..\tempOutput.txt"
            myProcess.Start()

            myProcess.WaitForExit(2000)

            If myProcess.HasExited = False Then
                myProcess.Kill()
            End If

            Dim dataStrings As String() = ReadFile()
            My.Computer.FileSystem.DeleteFile(batPath & "..\..\tempOutput.txt")

            If dataStrings.First.Equals(String.Empty) Then
                Throw New Exception("No card inserted or no card reader")
            End If

            Dim nameLines As String() = TrimStrings(dataStrings, 10, 6)
            Dim icLines As String() = TrimStrings(dataStrings, 22, 1)

            Dim name As String = ""
            Dim icNumber As String = ""
            Dim index As Integer

            ' Retrieving name from the nameLines
            For index = 0 To nameLines.Length - 1
                If Not nameLines(index).Substring(48).Replace(" ", "").Equals(String.Empty) Then
                    name &= (nameLines(index).Substring(48))
                End If
            Next

            ' Remove (if any) spaces at the back of name
            For index = name.Length - 1 To 0 Step -1
                If name(index) <> " " Then
                    Exit For
                End If
            Next

            name = name.Substring(0, index + 1)

            ' Retrieving IC Number from idLines
            icNumber = icLines(0).Substring(38).Replace(" ", "")



            ''''''''''''''''
            ' Check database, using icNumber
            ' If not existed in database, check date
            ' If existed, throw exception and display message
            '''''''''''''''''
            ' Connect to database
            Dim dbconn As New DatabaseConnection(dbPath)

            ' Getting age number from IC Number
            ' Then extracts birth date from the first 6 characters in icNumber
            Dim birthYear As Integer = Integer.Parse(icNumber.Substring(0, 2))
            birthYear += If(birthYear >= Date.Now.Year Mod 100, 1900, 2000)
            Dim birthPassed As Boolean = False
            Dim birthMonth As Integer = Integer.Parse(icNumber.Substring(2, 2))
            Dim birthDay As Integer = Integer.Parse(icNumber.Substring(4, 2))

            If birthMonth < Date.Now.Month Then
                birthPassed = True
            ElseIf birthMonth = Date.Now.Month Then
                If birthDay <= Date.Now.Day Then
                    birthPassed = True
                End If
            End If

            Dim age As Integer = Date.Now.Year - birthYear - If(birthPassed, 0, 1)

            nameTBox.Text = name
            ICTBox.Text = icNumber
            ageTBox.Text = age.ToString()

            If dbconn.IsExistedInDB(icNumber) = True Then
                QualifiedLabel.Text = "Not Qualified"
                QualifiedLabel.BackColor = Color.Red
                Throw New Exception("Reason : Already registered")
            End If

            If age < 54 Then
                QualifiedLabel.Text = "Not Qualified"
                QualifiedLabel.BackColor = Color.Red
                Throw New Exception("Reason : Age less than 55")
            End If

            If age = 54 Then
                If birthMonth >= Date.Now.Month Or birthDay > Date.Now.Day Then
                    QualifiedLabel.Text = "Not Qualified"
                    QualifiedLabel.BackColor = Color.Red
                    Throw New Exception("Reason : Age almost but not fully 55")
                End If
            End If

            QualifiedLabel.Text = "Qualified"
            QualifiedLabel.BackColor = Color.LawnGreen

            dbconn.AddToDB(icNumber, name, age)
        Catch ex As Exception
            MessageBox.Show(ex.Message)
        End Try
    End Sub

    Private Function ReadFile() As String()
        ' Open output file
        Dim FileObj As FileReader = New FileReader(batPath & "..\..\tempOutput.txt")
        FileObj.OpenFile()
        Dim data As String() = FileObj.GetData() 'File Data
        Return data
    End Function

    Private Function TrimStrings(ByVal dtStr As String(), ByVal startIndex As Integer, ByVal numLines As Integer) As String()
        Dim newString(numLines - 1) As String
        Dim newIndex As Integer = 0

        For index = startIndex To startIndex + numLines - 1
            newString(newIndex) = dtStr(index)
            newIndex += 1
        Next

        Return newString
    End Function

    Private Sub ClearForm()
        nameTBox.Clear()
        ICTBox.Clear()
        ageTBox.Clear()
        QualifiedLabel.Text = "-"
        QualifiedLabel.BackColor = Color.White
    End Sub

    Private Sub ClearBtn_Click(sender As Object, e As EventArgs) Handles ClearBtn.Click
        ClearForm()
    End Sub

    Private Sub ExitBtn_Click(sender As Object, e As EventArgs) Handles ExitBtn.Click
        CardReadAppForm.ActiveForm.Close()
    End Sub

    Private Sub BrowseDatabaseBtn_Click(sender As Object, e As EventArgs) Handles BrowseDatabaseBtn.Click
        Dim dbPathResult = DatabaseFileDialog.ShowDialog()

        If dbPathResult = DialogResult.OK Then
            Dim extStr As String = Path.GetExtension(DatabaseFileDialog.FileName)

            If DatabaseFileDialog.Filter.Contains(extStr) = True Then
                Me.dbPath = DatabaseFileDialog.FileName
                dbOKLabel.Visible = True

                If Me.batPath <> "" Then
                    ReadCardBtn.Enabled = True
                End If
            Else
                MessageBox.Show("Invalid file")
                Me.dbPath = ""
                dbOKLabel.Visible = False
                ReadCardBtn.Enabled = False
            End If
        End If
    End Sub

    Private Sub BrowseBatBtn_Click(sender As Object, e As EventArgs) Handles BrowseBatBtn.Click
        Dim batPathResult = BatchFileDialog.ShowDialog()

        If batPathResult = DialogResult.OK Then
            Dim extStr As String = Path.GetExtension(BatchFileDialog.FileName)

            If BatchFileDialog.Filter.Contains(extStr) = True Then
                Me.batPath = BatchFileDialog.FileName
                batOKLabel.Visible = True

                If Me.dbPath <> "" Then
                    ReadCardBtn.Enabled = True
                End If
            Else
                MessageBox.Show("Invalid file")
                Me.batPath = ""
                batOKLabel.Visible = False
                ReadCardBtn.Enabled = False
            End If
        End If
    End Sub
End Class