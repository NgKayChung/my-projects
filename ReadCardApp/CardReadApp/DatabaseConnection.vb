Imports System.Data

Public Class DatabaseConnection
    Private SqlConnection As New OleDb.OleDbConnection
    Private SqlCommand As New OleDb.OleDbCommand
    Private SqlReader As OleDb.OleDbDataReader

    Sub New(dbPath As String)
        SqlConnection.ConnectionString = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=" & dbPath & ";"
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

    Public Sub Close()
        SqlCommand.Dispose()
        SqlConnection.Close()
        SqlConnection.Dispose()
    End Sub
End Class