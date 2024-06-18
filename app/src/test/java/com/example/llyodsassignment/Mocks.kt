package com.example.llyodsassignment

import com.example.llyodsassignment.data.db.entities.CatImageResponseEntity
import com.example.llyodsassignment.data.model.CatImageResponseDto
import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel

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

    val updatedCatImageResponseEntity = listOf(
        CatImageResponseEntity(
            id = "3",
            url = "url3",
            width = 3,
            height = 4
        ),
        CatImageResponseEntity(
            id = "4",
            url = "url4",
            width = 3,
            height = 4
        )
    )

 val catImageResponseDto = listOf(
        CatImageResponseDto(
            id = "1",
            url = "url",
            width = 1,
            height = 2
        ),
        CatImageResponseDto(
            id = "2",
            url = "url",
            width = 1,
            height = 2
        )
    )

  val catImageResponse = listOf(
        CatImageResponse(
            id = "1",
            url = "url",
            width = 1,
            height = 2
        ),
        CatImageResponse(
            id = "2",
            url = "url",
            width = 1,
            height = 2
        )
    )

    val catImagesUiModel = listOf(
        CatImagesUiModel(
            id = "1",
            url = "url",
            width = 1,
            height = 2
        ),
        CatImagesUiModel(
            id = "2",
            url = "url",
            width = 1,
            height = 2
        )
    )

    val catImagesModel =  CatImagesUiModel(
        id = "1",
        url = "url",
        width = 1,
        height = 2
    )
}