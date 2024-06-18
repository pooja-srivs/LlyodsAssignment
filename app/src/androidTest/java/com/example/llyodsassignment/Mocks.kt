package com.example.llyodsassignment

import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity

object Mocks {

    val catImageResponseEntity = listOf(
        CatImageResponseEntity(
            id = "1",
            url = "url",
            width = 1,
            height = 2
        ),
        CatImageResponseEntity(
            id = "2",
            url = "url",
            width = 1,
            height = 2
        )
    )

    val mockResponseBody = """[{"id":"lj","url":"https://cdn2.thecatapi.com/images/lj.jpg","width":600,"height":450},{"id":"28j","url":"https://cdn2.thecatapi.com/images/28j.jpg","width":624,"height":403},{"id":"2gn","url":"https://cdn2.thecatapi.com/images/2gn.jpg","width":500,"height":333},{"id":"cgo","url":"https://cdn2.thecatapi.com/images/cgo.jpg","width":600,"height":740},{"id":"ci1","url":"https://cdn2.thecatapi.com/images/ci1.jpg","width":640,"height":464},{"id":"cqd","url":"https://cdn2.thecatapi.com/images/cqd.jpg","width":580,"height":872},{"id":"dql","url":"https://cdn2.thecatapi.com/images/dql.jpg","width":2448,"height":1836},{"id":"e67","url":"https://cdn2.thecatapi.com/images/e67.jpg","width":500,"height":333},{"id":"MTczOTM3NQ","url":"https://cdn2.thecatapi.com/images/MTczOTM3NQ.gif","width":500,"height":281},{"id":"MTgzNjYwMQ","url":"https://cdn2.thecatapi.com/images/MTgzNjYwMQ.jpg","width":512,"height":486}]""".trimIndent()
}