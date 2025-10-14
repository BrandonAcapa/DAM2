try {
    Connection conexion = DriverManager.getConnection(
            "jdbc:mysql://127.0.0.1:3306",
            "root", "root"
    );

    Statement statement = conexion.createStatement();

} catch (SQLException exception) {
    exception.printStackTrace();

} finally {
    try { if (resultSet != null) resultSet.close();} catch
    (Exception e) { e.printStackTrace();}
    try { if (statement != null) statement.close();} catch
    (Exception e) {}
    try { if (conexion != null) conexion.close();} catch
    (Exception e) {}
}