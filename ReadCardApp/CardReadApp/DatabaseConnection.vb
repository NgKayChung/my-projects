Imports System.Data

Public Class DatabaseConnection
    Private SqlConnection As New OleDb.OleDbConnection
    Private SqlCommand As New OleDb.OleDbCommand
    Private SqlReader As OleDb.OleDbDataReader

    Sub New(conn As String)
        SqlConnection.ConnectionString = conn
        SqlCommand = SqlConnection.CreateCommand()
        SqlConnection.Open()
    End Sub

    Public Function IsExistedInDB(ic As String) As Boolean
        SqlCommand.CommandText = "SELECT ICNumber FROM Record WHERE ICNumber=" & Chr(34) & ic & Chr(34) & ";"

        SqlReader = SqlCommand.ExecuteReader()

        Dim exist As Boolean = SqlReader.Read()

        SqlReader.Close()

        Return exist
    End Function

    Public Function AddToDB(ic As String, name As String, age As String) As Boolean
        SqlCommand.CommandText = "INSERT INTO Record(ICNumber, origName, age) VALUES(" & Chr(34) & ic & Chr(34) & ", " & Chr(34) & name & Chr(34) & ", " & age & ");"

        SqlReader = SqlCommand.ExecuteReader()

        Dim success As Boolean = SqlReader.Read()

        SqlReader.Close()

        Return success
    End Function

    Public Function GetData(icKey As String) As String()
        SqlCommand.CommandText = "SELECT * FROM Record WHERE ICNumber=" & Chr(34) & icKey & Chr(34) & ";"

        SqlReader = SqlCommand.ExecuteReader()
        SqlReader.Read()

        Dim rec As String() = {SqlReader.Item(0), SqlReader.Item(1), SqlReader.Item(2).ToString()}

        SqlReader.Close()

        Return rec
    End Function

    Public Sub Close()
        SqlCommand.Dispose()
        SqlConnection.Close()
        SqlConnection.Dispose()
    End Sub
End Class