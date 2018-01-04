Imports System.IO

Public Class FileReader
    Private FilePath As String
    Private LineNumber As Integer = 0

    Public Sub New(path As String)
        FilePath = path
    End Sub

    Public Sub OpenFile()
        Dim file = My.Computer.FileSystem.OpenTextFileReader(FilePath)
        GetLineNumbers(file)
        file.Close()
    End Sub

    Public Sub GetLineNumbers(fileStream As StreamReader)
        While fileStream.EndOfStream = False
            fileStream.ReadLine()
            LineNumber += 1
        End While
    End Sub

    Public Function GetData() As String()
        Dim file = My.Computer.FileSystem.OpenTextFileReader(FilePath)
        Dim lineData(LineNumber) As String
        Dim index As Integer = 0

        For index = 0 To lineData.Length - 1
            lineData(index) = file.ReadLine()
        Next

        file.Close()

        Return lineData
    End Function
End Class
