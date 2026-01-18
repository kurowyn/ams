import sqlite3

db_path = r"c:\Users\HP\Desktop\min project v2\gestion_absence.db"

print("=" * 70)
print("Testing AbsenceDAO.getAllAbsences() SQL Query")
print("=" * 70)

conn = sqlite3.connect(db_path)
cursor = conn.cursor()

# First, check what tables exist
print("\n1. Checking existing tables...")
cursor.execute("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")
tables = [row[0] for row in cursor.fetchall()]
print(f"   Tables found: {tables}")

# Check if required tables exist
required_tables = ['absence_table', 'student_table', 'subject_table']
missing_tables = [t for t in required_tables if t not in tables]

if missing_tables:
    print(f"\n   ⚠ WARNING: Missing required tables: {missing_tables}")
    print("   → You need to run 'python init_database.py' first!")
else:
    print(f"   ✓ All required tables exist")

# Check row counts
print("\n2. Checking table row counts...")
for table in required_tables:
    if table in tables:
        cursor.execute(f"SELECT COUNT(*) FROM {table}")
        count = cursor.fetchone()[0]
        print(f"   {table:20s}: {count} rows")

# Test the actual SQL query from AbsenceDAO.getAllAbsences()
print("\n3. Testing AbsenceDAO.getAllAbsences() SQL query...")
print("   Query:")
sql = """SELECT a.*, s.first_name, s.last_name, sub.subject_name, c.class_level 
FROM absence_table a 
JOIN student_table s ON a.id_student = s.id_student 
JOIN subject_table sub ON a.id_subject = sub.id_subject
JOIN class_table c ON s.id_class = c.id_class"""
print(f"   {sql}")

try:
    cursor.execute(sql)
    results = cursor.fetchall()
    
    print(f"\n   ✓ Query executed successfully!")
    print(f"   Results: {len(results)} absence records found")
    
    if len(results) > 0:
        print("\n4. Sample absence records:")
        # Get column names
        col_names = [description[0] for description in cursor.description]
        print(f"   Columns: {col_names}")
        
        for i, row in enumerate(results[:5], 1):  # Show first 5
            print(f"\n   Record {i}:")
            print(f"     Student: {row[col_names.index('first_name')]} {row[col_names.index('last_name')]}")
            print(f"     Subject: {row[col_names.index('subject_name')]}")
            print(f"     Date: {row[col_names.index('absence_date')]}")
            print(f"     Count: {row[col_names.index('absence_count')]}")
    else:
        print("\n   ℹ No absence records in database yet")
        print("   → This is normal if you haven't recorded any absences")
        
except sqlite3.Error as e:
    print(f"\n   ✗ Query FAILED!")
    print(f"   Error: {e}")
    print("\n   Possible causes:")
    print("   1. Missing tables (run 'python init_database.py')")
    print("   2. Missing columns in tables")
    print("   3. Database corruption")

conn.close()

print("\n" + "=" * 70)
print("Test complete")
print("=" * 70)
