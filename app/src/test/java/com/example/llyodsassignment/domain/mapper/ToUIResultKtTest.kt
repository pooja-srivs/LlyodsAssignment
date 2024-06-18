package com.example.llyodsassignment.domain.mapper

import com.example.llyodsassignment.Mocks
import com.example.llyodsassignment.domain.model.CatImageResponse
import com.example.llyodsassignment.ui.view.model.CatImagesUiModel
import org.junit.Assert
import org.junit.Test

class ToUIResultKtTest {

    @Test
    fun `test mapping from CatImageResponse to CatImagesUiModel with multiple items`() {
        val catImageResponses = Mocks.catImageResponse
        val expectedUiModels = Mocks.catImagesUiModel

        val result = catImageResponses.toUiModel()

        Assert.assertEquals(expectedUiModels, result)
    }

    @Test
    fun `test mapping from CatImageResponse to CatImagesUiModel with empty list`() {
        val catImageResponses = emptyList<CatImageResponse>()
        val expectedUiModels = emptyList<CatImagesUiModel>()

        val result = catImageResponses.toUiModel()

        Assert.assertEquals(expectedUiModels, result)
    }

    @Test
    fun `test mapping from CatImageResponse to CatImagesUiModel with single item`() {
        val catImageResponses = emptyList<CatImageResponse>()
        val expectedUiModels = emptyList<CatImagesUiModel>()

        val result = catImageResponses.toUiModel()

        Assert.assertEquals(expectedUiModels, result)
    }

}