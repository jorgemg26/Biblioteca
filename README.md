🚀 Overview

This project is a library management system developed in Java using JDBC and PostgreSQL. It follows a layered architecture to ensure clean separation of concerns, maintainability, and scalability.

The application allows managing books, searching by title, and handling loans and returns while ensuring data consistency through transactions.

🧠 Architecture

The project is structured using a layered architecture:

🟢 Model

Represents database entities as Java objects:

Libro (Book)
Usuario (User)
Prestamo (Loan)

These classes only store data (POJOs) and do not contain business logic.

🔵 Repository

Handles database access using JDBC:

Executes SQL queries (CRUD operations)
Maps database rows to Java objects

Main classes:

LibroRepositoryInterface
LibroRepositoryJDBC
PrestamoRepositoryJDBC
🟡 Service

Contains business logic and rules:

Validates input data
Controls application logic (e.g., checking availability before borrowing)
Manages transactions

Main classes:

LibroService
PrestamoService
TxLibroService
🔴 Database
PostgreSQL database
Managed through JDBC (Db.java)
Includes constraints to ensure data integrity (e.g., preventing multiple active loans for the same book)
⚙️ Features
📚 Book management (Create, Read, Update, Delete)
🔍 Search books by title
🔄 Loan system (borrow and return books)
✅ Availability control (no duplicate active loans)
💥 Transaction management (commit & rollback)
🧪 Demo classes for testing functionality
🔁 Transaction Flow

When a book is borrowed:

Check if the book is available
Insert loan into database
Mark book as unavailable
Commit transaction

If any step fails:

Rollback is executed
No changes are saved
🛠️ Technologies Used
Java
JDBC
PostgreSQL
SQL
▶️ How to Run
Configure your database in db.properties
Execute the SQL script to create tables
Run any of the demo classes:
Main → basic usage and search
CrudDemo → CRUD operations
CapasDemo → service layer usage
PrestamoDemo → loan system
TxDemo → transaction demo
🎯 Purpose

This project was built to practice core backend development concepts:

Layered architecture
Database interaction using JDBC
Transaction management
Clean and maintainable code structure
