import sqlite3
import os

db_path = r"c:\Users\HP\Desktop\min project v2\gestion_absence.db"
sql_file = r"c:\Users\HP\Desktop\min project v2\database_setup_sqlite.sql"

print("=" * 60)
print("SQLite Database Initialization")
print("=" * 60)

# Check if database exists
if os.path.exists(db_path):
    print(f"\n✓ Database file found: {db_path}")
    backup = db_path + ".backup"
    print(f"  Creating backup: {backup}")
    import shutil
    shutil.copy2(db_path, backup)
else:
    print(f"\n✓ Creating new database: {db_path}")

# Read SQL files
print(f"\n✓ Reading Schema script: {sql_file}")
with open(sql_file, 'r', encoding='utf-8') as f:
    sql_script = f.read()

data_file = r"c:\Users\HP\Desktop\min project v2\database_data.sql"
print(f"✓ Reading Data script: {data_file}")
with open(data_file, 'r', encoding='utf-8') as f:
    data_script = f.read()

# Connect and execute
print(f"\n✓ Connecting to database...")
conn = sqlite3.connect(db_path)
cursor = conn.cursor()

print(f"✓ Executing Schema script...")
try:
    cursor.executescript(sql_script)
    print(f"✓ Schema created successfully!")
    
    print(f"✓ Executing Data script...")
    cursor.executescript(data_script)
    print(f"✓ Data populated successfully!")
    
    conn.commit()
except Exception as e:
    print(f"✗ Error executing script: {e}")
    conn.rollback()
    exit(1)

# Verify tables
print(f"\n" + "=" * 60)
print("Verification - Tables Created")
print("=" * 60)

cursor.execute("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")
tables = cursor.fetchall()

print(f"\nTotal tables: {len(tables)}")
for i, table in enumerate(tables, 1):
    table_name = table[0]
    cursor.execute(f"SELECT COUNT(*) FROM {table_name}")
    count = cursor.fetchone()[0]
    print(f"{i}. {table_name:25s} - {count} rows")

conn.close()

print("\n" + "=" * 60)
print("✓ Database initialization complete!")
print("=" * 60)
