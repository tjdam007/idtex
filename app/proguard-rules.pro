# proguard-rules.pro

# Add project-specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# Hide the original source file name if you keep the line number information.
-renamesourcefileattribute SourceFile

# Keep all your ViewModel classes to ensure they are not obfuscated
-keep class com.example.idtex.viewmodels.** { *; }
-keep class com.example.idtex.entities.** { *; }

# Keep LiveData classes
-keep class androidx.lifecycle.LiveData { *; }
-keepclassmembers class androidx.lifecycle.LiveData { *; }

# Retrofit & OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.google.gson.** { *; }

# Coroutine
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

# RecyclerView
-keep class androidx.recyclerview.widget.** { *; }
-keepclassmembers class androidx.recyclerview.widget.** { *; }

# Core libraries
-keep class androidx.core.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }
-keep class androidx.fragment.app.** { *; }
-keep class androidx.activity.** { *; }