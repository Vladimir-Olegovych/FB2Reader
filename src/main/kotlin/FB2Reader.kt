package org.example

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class FB2Reader(private val file: File) {

    private val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    private val document = builder.parse(file)
    private val root = document.documentElement
    private val linesList = root.getElementsByTagName("p")

    fun getFile(): File = file
    fun getTitle(): String = root.getElementsByTagName("book-title").item(0).textContent
    fun getAuthor(): String = root.getElementsByTagName("author").item(0).textContent

    fun getText(): String {
        val result = StringBuilder()
        for (i in 0 until linesList.length) {
            result.append(linesList.item(i).textContent)
            result.append("\n")
        }
        return result.toString()
    }
    fun getPages(linesOnPage: Int): ArrayList<String> {
        val pages = ArrayList<String>()
        val currentPage = StringBuilder()
        var currentLinesOnPage = 0
        for (i in 0 until linesList.length){
            currentPage.append(linesList.item(i).textContent)
            currentPage.append("\n")
            currentLinesOnPage++

            if (currentLinesOnPage == linesOnPage){
                pages.add(currentPage.toString())
                currentPage.clear()
                currentLinesOnPage = 0
            }
        }
        if (currentPage.isNotEmpty()) pages.add(currentPage.toString())
        return pages
    }


}