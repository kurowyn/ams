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
print(f"Database: {db_path}")
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
        print(f"  {col[1]} ({col[2]}){' PRIMARY KEY' if col[5] else ''}")
    
    # Get row count
    cursor.execute(f"SELECT COUNT(*) FROM {table_name}")
    count = cursor.fetchone()[0]
    print(f"\nRow count: {count}")
    
    # Show sample data (first 3 rows)
    if count > 0:
        cursor.execute(f"SELECT * FROM {table_name} LIMIT 3")
        rows = cursor.fetchall()
        print(f"\nSample data (first {min(3, count)} rows):")
        for row in rows:
            print(f"  {row}")

conn.close()
print("\n" + "=" * 60)
