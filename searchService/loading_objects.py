class Sponsor:

    def __init__(self, name: str, location: str, description: str):
        self.row = {
            'name': name,
            'location': location,
            'description': description
        }

    def deconstruct_row(self):
        return ','.join(list(self.row.values()))

    def generate_insert(self, table: str):
        return f"INSERT INTO {table} (name, location, description) VALUES ({self.deconstruct_row()})"
