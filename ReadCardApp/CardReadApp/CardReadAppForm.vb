Imports System.Diagnostics
Imports System.IO

Public Class CardReadAppForm
    Private Sub CardReadAppForm_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.ActiveControl = Me.ReadCardBtn
    End Sub

    Private Sub CardReadAppForm_Close(sender As Object, e As EventArgs) Handles MyBase.Closing
        ClearForm()
    End Sub

    Private Sub ReadCardBtn_Click(sender As Object, e As EventArgs) Handles ReadCardBtn.Click
        Try
            ' Clear form before starts reading card
            ClearForm()

            ' Start running batch file
            Dim myProcess As New Process
            myProcess.StartInfo.UseShellExecute = False
            myProcess.StartInfo.FileName = "\\HAPPY-MOMENTS\Build\Scripts\readCard.bat"
            myProcess.StartInfo.RedirectStandardOutput = True
            myProcess.StartInfo.CreateNoWindow = True
            myProcess.Start()

            Me.Cursor = Cursors.WaitCursor

            While myProcess.HasExited = False
                myProcess.WaitForExit(1000)
            End While

            Me.Cursor = Cursors.Default

            Dim dataStrings As String() = myProcess.StandardOutput.ReadToEnd().Split(vbCrLf)
            dataStrings = TrimStrings(dataStrings, 2, dataStrings.Length - 2)
            myProcess.Close()

            If dataStrings.First.Equals(String.Empty) Then
                Throw New Exception("No card inserted or no card reader")
            End If

            myProcess.StartInfo.FileName = "\\HAPPY-MOMENTS\Build\temp\scdump.exe"
            myProcess.StartInfo.RedirectStandardOutput = False
            myProcess.StartInfo.CreateNoWindow = False
            myProcess.Start()
            System.Threading.Thread.Sleep(1000)
            SendKeys.Send("{ENTER}")
            System.Threading.Thread.Sleep(1000)
            SendKeys.Send("{ENTER}")

            Me.Cursor = Cursors.WaitCursor

            While myProcess.HasExited = False
                myProcess.WaitForExit(1000)
            End While

            Me.Cursor = Cursors.Default

            myProcess.Close()
            myProcess.Dispose()

            Dim icLines As String() = TrimStrings(dataStrings, 22, 1)
            Dim icNumber As String = ""

            ' Retrieving IC Number from idLines
            icNumber = icLines(0).Substring(39).Replace(" ", "")

            ''''''''''''''''
            ' Check database, using icNumber
            ' If not existed in database, check date
            ' If existed, throw exception and display message
            '''''''''''''''''
            ' Connect to database
            Dim dbconn As New DatabaseConnection("Provider=Microsoft.ACE.OLEDB.16.0;Data Source=" & "\\HAPPY-MOMENTS\Build\DB\CharityDB.accdb" & ";Jet OLEDB:Database Password=happy32momentum;")

            If dbconn.IsExistedInDB(icNumber) = True Then
                QualifiedLabel.Text = "Not Qualified"
                QualifiedLabel.BackColor = Color.Red
                Dim record As String() = dbconn.GetData(icNumber)
                nameTBox.Text = record(1)
                ICTBox.Text = record(0)
                ageTBox.Text = record(2)
                ICPic.ImageLocation = "photo.jpg"
                dbconn.Close()
                Throw New Exception("Reason : Already registered")
            End If

            Dim nameLines As String() = TrimStrings(dataStrings, 10, 6)

            Dim name As String = ""
            Dim index As Integer

            ' Retrieving name from the nameLines
            For index = 0 To nameLines.Length - 1
                If Not nameLines(index).Substring(49).Replace(" ", "").Equals(String.Empty) Then
                    name &= (nameLines(index).Substring(49))
                End If
            Next

            ' Remove (if any) spaces at the back of name
            For index = name.Length - 1 To 0 Step -1
                If name(index) <> " " Then
                    Exit For
                End If
            Next

            name = name.Substring(0, index + 1)

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
            ICPic.ImageLocation = "photo.jpg"

            'If age < 59 Then
            '    QualifiedLabel.Text = "Not Qualified"
            '    QualifiedLabel.BackColor = Color.Red
            '    dbconn.Close()
            '    Throw New Exception("Reason : Age less than 60")
            'End If

            'If age = 59 Then
            '    If birthMonth >= Date.Now.Month Or birthDay > Date.Now.Day Then
            '        QualifiedLabel.Text = "Not Qualified"
            '        QualifiedLabel.BackColor = Color.Red
            '        dbconn.Close()
            '        Throw New Exception("Reason : Age almost but not fully 60")
            '    End If
            'End If

            QualifiedLabel.Text = "Qualified"
            QualifiedLabel.BackColor = Color.LawnGreen

            dbconn.AddToDB(icNumber, name, age)
            dbconn.Close()

            ' backup database by making a copy to the backup folder(hidden)
            Dim backupProc As New Process
            backupProc.StartInfo.UseShellExecute = True
            backupProc.StartInfo.RedirectStandardOutput = False
            backupProc.StartInfo.CreateNoWindow = False
            backupProc.StartInfo.FileName = "\\HAPPY-MOMENTS\Build\Scripts\backup.vbs"
            backupProc.Start()

            While backupProc.HasExited = False
                backupProc.WaitForExit(2000)
            End While

            backupProc.Close()
            backupProc.Dispose()
        Catch ex As Exception
            MessageBox.Show(ex.Message)
        End Try
    End Sub

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
        ICPic.ImageLocation = ""
        Dim n As Integer = 1
        While My.Computer.FileSystem.FileExists("jpn" & n.ToString())
            My.Computer.FileSystem.DeleteFile("jpn" & n.ToString())
            n += 1
        End While
        If My.Computer.FileSystem.FileExists("photo.jpg") Then
            My.Computer.FileSystem.DeleteFile("photo.jpg")
        End If
    End Sub

    Private Sub ClearBtn_Click(sender As Object, e As EventArgs) Handles ClearBtn.Click
        ClearForm()
    End Sub

    Private Sub ExitBtn_Click(sender As Object, e As EventArgs) Handles ExitBtn.Click
        ClearForm()
        CardReadAppForm.ActiveForm.Close()
    End Sub
End Class