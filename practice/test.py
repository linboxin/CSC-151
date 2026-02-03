class Node: 
    def __init__(self, data):
        self.data = data
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = None
        
    def insert(self, test): 
        if self.head.next == None:
            self.head.next = Node()