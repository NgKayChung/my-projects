Imports System.Diagnostics

Public Class Form1
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Dim myProcess As Process = Process.Start("C:\Users\HAPPY MOMENTS\Documents\testbatch.bat")

        myProcess.WaitForExit(2000)

        If myProcess.HasExited = False Then
            myProcess.Kill()
        End If

        Try
            Dim dataStrings As String() = ReadFile()
            Dim nameLines As String() = TrimStrings(dataStrings, 10, 6)
            Dim icLines As String() = TrimStrings(dataStrings, 22, 1)

            Dim name As String = ""
            Dim icNumber As String = ""
            Dim index As Integer

            ' Retrieving name from the nameLines
            For index = 0 To nameLines.Length - 1
                If Not nameLines(index).Substring(48).Replace(" ", "").Equals(String.Empty) Then
                    name &= (nameLines(index).Substring(48).Replace(" ", ""))
                End If
            Next

            ' Retrieving IC Number from idLines
            icNumber = icLines(0).Substring(38).Replace(" ", "")

            ''''''''''''''''
            ' Check database here, via name | icNumber
            ' If not existed in database, check date
            ' If existed, throw exception and display message
            '''''''''''''''''
            Dim dbconn As New DatabaseConnection("C:\Users\HAPPY MOMENTS\Documents\CharityDB.accdb")

            If dbconn.IsExistedInDB(icNumber) = True Then
                Throw New Exception("IC Number : " & icNumber & " Not Qualified" & vbCrLf & "Reason : Already registered")
            End If

            ' Getting age number from IC Number
            ' Then extracts birth date from the first 6 characters in icNumber
            Dim age As Integer = (Date.Now.Year - 1900 - Integer.Parse(icNumber.Substring(0, 2)))

            If age < 55 Then
                Throw New Exception("Age : " + age.ToString() + " Not Qualified")
            End If

            If age = 55 Then
                Dim birthMonth As Integer = Integer.Parse(icNumber.Substring(2, 2))

                If birthMonth > Date.Now.Month Then
                    Throw New Exception("Age : " + (age - 1).ToString() + " Not Qualified" + vbCrLf + "Reason : Not fully 55")
                ElseIf birthMonth = Date.Now.Month Then
                    Dim birthDay As Integer = Integer.Parse(icNumber.Substring(4, 2))

                    If birthDay > Date.Now.Day Then
                        Throw New Exception("Age : " + (age - 1).ToString() + " Not Qualified" + vbCrLf + "Reason : Not fully 55")
                    End If
                End If
            End If

            dbconn.AddToDB(icNumber, name, age)

            MessageBox.Show("Age : " + age.ToString() + " Qualified")
        Catch ex As Exception
            MessageBox.Show(ex.Message)
        End Try

    End Sub

    Private Function ReadFile() As String()
        Dim FileObj As FileReader = New FileReader("C:\xampp\test.txt")
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
End Class
