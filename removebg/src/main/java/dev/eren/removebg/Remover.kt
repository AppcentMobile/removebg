package dev.eren.removebg

import kotlinx.coroutines.flow.Flow

/**
 * Created by erenalpaslan on 11.09.2023
 */
interface Remover <T> {

    fun clearBackground(image: T): Flow<T?>

    fun getMaskedImage(input: T, mask: T): T

}