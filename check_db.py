import sqlite3
import os

db_path = r"c:\Users\HP\Desktop\min project v2\gestion_absence.db"

if not os.path.exists(db_path):
    print(f"Database file not found: {db_path}")
    exit(1)

conn = sqlite3.connect(db_path)
cursor = conn.cursor()

# Get all tables
cursor.execute("SELECT name FROM sqlite_master WHERE type='table'")
tables = cursor.fetchall()

print("=" * 60)
print(f"Database: gestion_absence.db")
print("=" * 60)
print(f"\nTables found: {len(tables)}")
for table in tables:
    print(f"  - {table[0]}")

# For each table, show schema and row count
for table in tables:
    table_name = table[0]
    print(f"\n{'=' * 60}")
    print(f"Table: {table_name}")
    print('=' * 60)
    
    # Get schema
    cursor.execute(f"PRAGMA table_info({table_name})")
    columns = cursor.fetchall()
    print("\nColumns:")
    for col in columns:
        pk_marker = ' PRIMARY KEY' if col[5] else ''
        not_null = ' NOT NULL' if col[3] else ''
        print(f"  - {col[1]} ({col[2]}){pk_marker}{not_null}")
    
    # Get row count
    cursor.execute(f"SELECT COUNT(*) FROM {table_name}")
    count = cursor.fetchone()[0]
    print(f"\nRow count: {count}")

conn.close()
print("\n" + "=" * 60)
