package covidInfo

import java.io.File
import java.io.FileWriter

fun createFolderIfNotExists(path: String) {
    val folder = File(path)

    if (!folder.exists()) {
        if (!folder.mkdir()) {
            throw Exception("Couldn't create directory $path")
        }
    }
}

fun saveToDisk(filename: String, file: String) {
    val fileWriter = FileWriter(filename)
    fileWriter.write(file)
    fileWriter.close()
}
