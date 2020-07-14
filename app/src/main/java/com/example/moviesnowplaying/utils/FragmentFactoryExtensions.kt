package com.example.moviesnowplaying.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

inline fun <reified T : Fragment> FragmentFactory.newInstance(): T {
    return instantiate(
        T::class.java.classLoader!!,  // note: could return null if primitive
        T::class.java.name
    ) as T
}
