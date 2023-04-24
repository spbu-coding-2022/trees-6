package bstrees.dataBases.reps

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import bstrees.dataBases.SerializableTree
import kotlinx.serialization.encodeToString
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import kotlin.io.path.Path


object JsonTreeRepo : DBTreeRepo {
    private fun createDirPaths() {
        File("JSONTreeRep").mkdir()
        File(Path("JSONTreeRep", "BSTree").toUri()).mkdir()
        File(Path("JSONTreeRep", "RBTree").toUri()).mkdir()
        File(Path("JSONTreeRep", "AvlTree").toUri()).mkdir()
    }

    private fun getPathToFile(typeTree: String, treeName: String): String {
        return Path("JSONTreeRep", typeTree, "${treeName}.json").toString()
    }

    override fun getTree(treeType: String, treeName: String): SerializableTree? {
        createDirPaths()

        val filePath = getPathToFile(treeType, treeName)
        lateinit var file: FileReader
        var fileFound = true

        return try {
            file = FileReader(filePath)
            val jsonText = file.readText()

            Json.decodeFromString<SerializableTree>(jsonText)
        } catch (e: FileNotFoundException) {
            fileFound = false
            null
        } catch (e: Exception) {
            throw e
        } finally {
            if (fileFound) {
                file.close()
            }
        }
    }

    override fun setTree(serializableTree: SerializableTree) {
        createDirPaths()

        val filePath = getPathToFile(serializableTree.treeType, serializableTree.name)
        lateinit var file: FileWriter

        try {
            file = FileWriter(filePath)
            file.write(Json.encodeToString(serializableTree))
        } catch (e: Exception) {
            throw e
        } finally {
            file.flush()
            file.close()
        }
    }

    override fun deleteTree(treeType: String, treeName: String) {
        createDirPaths()

        val path = getPathToFile(treeType, treeName)

        try {
            File(path).delete()
        } catch (e: Exception) {
            throw e
        }
    }
}