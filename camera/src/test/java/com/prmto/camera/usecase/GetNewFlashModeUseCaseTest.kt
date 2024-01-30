package com.prmto.camera.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.camera.util.CameraFlashMode
import org.junit.Before
import org.junit.Test


class GetNewFlashModeUseCaseTest {

    private lateinit var getNewFlashModeUseCase: GetNewFlashModeUseCase

    @Before
    fun setUp() {
        getNewFlashModeUseCase = GetNewFlashModeUseCase()
    }

    @Test
    fun `getNewFlashModeUseCase, when currentFlashMode is OFF, then return ON`() {
        val currentFlashMode = CameraFlashMode.OFF
        val result = getNewFlashModeUseCase(currentFlashMode = currentFlashMode)
        assertThat(result).isEqualTo(CameraFlashMode.ON)
    }

    @Test
    fun `getNewFlashModeUseCase, when currentFlashMode is ON, then return AUTO`() {
        val currentFlashMode = CameraFlashMode.ON
        val result = getNewFlashModeUseCase(currentFlashMode = currentFlashMode)
        assertThat(result).isEqualTo(CameraFlashMode.AUTO)
    }

    @Test
    fun `getNewFlashModeUseCase, when currentFlashMode is AUTO, then return OFF`() {
        val currentFlashMode = CameraFlashMode.AUTO
        val result = getNewFlashModeUseCase(currentFlashMode = currentFlashMode)
        assertThat(result).isEqualTo(CameraFlashMode.OFF)
    }
}