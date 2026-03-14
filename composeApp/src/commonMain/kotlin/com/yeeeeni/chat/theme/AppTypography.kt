package com.yeeeeni.chat.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kmm_chat.composeapp.generated.resources.Res
import kmm_chat.composeapp.generated.resources.Pretendard_Bold
import kmm_chat.composeapp.generated.resources.Pretendard_Medium
import kmm_chat.composeapp.generated.resources.Pretendard_Regular
import org.jetbrains.compose.resources.Font

@Composable
fun AppFontFamily() = FontFamily(
    Font(Res.font.Pretendard_Regular, weight = FontWeight.Normal),
    Font(Res.font.Pretendard_Medium, weight = FontWeight.Medium),
    Font(Res.font.Pretendard_Bold, weight = FontWeight.Bold),
)

@Composable
fun AppTypography() = Typography(defaultFontFamily = AppFontFamily())