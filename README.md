# trees-6
`BSTrees` is a library that allows you to use 3 types of binary search trees: randomized BSTree, AVLTree, RBTree.

## How to use

To build the library run
```bash
  ./gradlew build
```
 To create each of the tree views, you can use:
 ```kotlin
val rbTree = RBTree<Int, Int>() // instantiate empty red-black tree
val avlTree = AvlTree<Int, String>() // instantiate empty AVL tree
val bsTree = BSTree<String, Int>() // instantiate empty simple tree
```
The first type parameter is a comparable key. \
The second type parameter is a stored value. It can be anything

Each tree supports 3 basic operations: `insert`, `find`, `delete`.
 ```kotlin
val bsTree = BSTree<String, Int>()

bsTree.insert("bruh", 5)
bsTree.find("bruh") // 5
bsTree.delete("bruh")
```

## Storing BSTs
You can store any tree in any of the three databases presented. These are `Neo4j`, `SQLite` and `Json`. You can choose the types of key and value on wich the tree is built, but supported only string and int if you want to store it

To use `Neo4j`, you need to install the desktop application `Doсker`, open it and perform `neo4j_up.sh` with with the necessary configurations, which are in the `CONTAINER.conf` file

# User Interface

You can interact with the trees directly using the provided user interface. It allows you to choose the database, the types of keys and values on which the tree is built, as well as save and unload trees from databases. Again, you can create and load any trees to any data bases.



## License
Distributed under the Apache 2.0 License. See [LICENSE](https://github.com/spbu-coding-2022/trees-6/blob/main/LICENSE) for more information.
