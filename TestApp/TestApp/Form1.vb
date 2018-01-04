Public Class Form1
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        System.Diagnostics.Process.Start("C:\Users\HAPPY MOMENTS\Documents\testbatch.bat")
        ReadFile()
    End Sub

    Private Sub ReadFile()
        Try
            Dim FileObj As FileReader = New FileReader("C:\xampp\test.txt")
            FileObj.OpenFile()
            Dim data As String() = FileObj.GetData() 'File Data

        Catch ex As Exception
            MessageBox.Show(ex.Message)
        End Try

    End Sub
End Class
