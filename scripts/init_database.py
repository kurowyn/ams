import sqlite3
import os

DB_PATH = r"..\db\gestion_absence.db"
SQL_FILE_PATH = r"schema.sql"

print("SQLite Database Initialization")
print(f"Reading Schema script: {SQL_FILE_PATH}")
with open(SQL_FILE_PATH, 'r', encoding='utf-8') as f:
    sql_script = f.read()

DATA_SCRIPT_FILE = r"populate_db.sql"
print(f"✓ Reading Data script: {DATA_SCRIPT_FILE}")
with open(DATA_SCRIPT_FILE, 'r', encoding='utf-8') as f:
    data_script = f.read()

# Connect and execute
print(f"Connecting to database...")
connection = sqlite3.connect(DB_PATH)
cursor = connection.cursor()

print(f"Executing Schema script...")
try:
    cursor.executescript(sql_script)
    print(f"Schema created successfully!")
    
    print(f"Executing Data script...")
    cursor.executescript(data_script)
    print(f"Data populated successfully!")
    
    connection.commit()
except Exception as e:
    print(f"Error executing script: {e}")
    connection.rollback()
    exit(1)

# Verify tables
print("Verification - Tables Created")

cursor.execute("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")
tables = cursor.fetchall()

print(f"\nTotal tables: {len(tables)}")
for i, table in enumerate(tables, 1):
    table_name = table[0]
    cursor.execute(f"SELECT COUNT(*) FROM {table_name}")
    count = cursor.fetchone()[0]
    print(f"{i}. {table_name:25s} - {count} rows")

connection.close()

print("Database initialization complete!")
