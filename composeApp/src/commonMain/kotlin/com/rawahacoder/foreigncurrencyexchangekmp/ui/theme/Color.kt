package com.rawahacoder.foreigncurrencyexchangekmp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable




val seed = Color(0xFF006B58)

val freshColor = Color(0xFF44FF78)
val staleColor = Color(0xFFFF9E44)

val primaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF86A8FC)
    else Color(0xFF283556)

val headerColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF0C0C0C)
    else Color(0xFF283556)

val surfaceColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF161616)
    else Color(0xFFFFFFFF)

val textColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF000000)


// LIGHT
val GreenPrimaryLight = Color(0xff006e26)
val OnGreenLight = Color(0xffffffff)
val GreenContainerLight = Color(0xff6cff82)
val OnGreenContainerLight = Color(0xff002106)

val GreenSecondaryLight = Color(0xff526350)
val OnGreenSecondaryLight = OnGreenLight
val GreenSecondaryContainerLight = Color(0xffd4e8d0)
val OnGreenSecondaryContainerLight = Color(0xff101f10)

val GreenTertiaryLight = Color(0xff39656b)
val OnGreenTertiaryLight = OnGreenLight
val GreenTertiaryContainerLight = Color(0xffbcebf2)
val OnGreenTertiaryContainerLight = Color(0xff001f23)

val ErrorLight = Color(0xffba1a1a)
val OnErrorLight = Color(0xffffffff)
val ErrorContainerLight = Color(0xffffdad6)
val OnErrorContainerLight = Color(0xff410002)

val BackgroundLight = Color(0xfffcfdf7)
val OnBackgroundLight = Color(0xff1a1c19)
val SurfaceLight = BackgroundLight
val OnSurfaceLight = OnBackgroundLight
val SurfaceVariantLight = Color(0xffdee5d9)
val OnSurfaceVariantLight = Color(0xff424940)

val OutlineLight = Color(0xff72796f)

// DARK
val GreenPrimaryDark = Color(0xff00e559)
val OnGreenDark = Color(0xff003910)
val GreenContainerDark = Color(0xff00531b)
val OnGreenContainerDark = Color(0xff6cff82)

val GreenSecondaryDark = Color(0xffb9ccb5)
val OnGreenSecondaryDark = OnGreenDark
val GreenSecondaryContainerDark = Color(0xff3a4b39)
val OnGreenSecondaryContainerDark = Color(0xffd4e8d0)

val GreenTertiaryDark = Color(0xffa1ced5)
val OnGreenTertiaryDark = Color(0xff00363c)
val GreenTertiaryContainerDark = Color(0xff1f4d53)
val OnGreenTertiaryContainerDark = Color(0xffbcebf2)

val ErrorDark = Color(0xffffb4ab)
val OnErrorDark = Color(0xff690005)
val ErrorContainerDark = Color(0xff93000a)
val OnErrorContainerDark = Color(0xffffdad6)

val BackgroundDark = Color(0xff1a1c19)
val OnBackgroundDark = Color(0xffe2e3dd)
val SurfaceDark = BackgroundDark
val OnSurfaceDark = OnBackgroundDark
val SurfaceVariantDark = Color(0xff424940)
val OnSurfaceVariantDark = Color(0xffc2c9bd)

val OutlineDark = Color(0xff72796f)