# Add project specific ProGuard rules here.
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Preserve all Kotlin metadata
-keepattributes *Annotation*, InnerClasses
-dontnote kotlin.**
-keep class kotlin.** { *; }
-keep class org.jetbrains.** { *; }

# Preserve Unity Ads classes
-keep class com.unity3d.ads.** { *; }
-dontwarn com.unity3d.ads.**

# Preserve Circle ImageView
-keep class de.hdodenhof.circleimageview.** { *; }

# Preserve Lottie animations
-keep class com.airbnb.lottie.** { *; }

# Preserve all application classes
-keep public class com.samgames.storyapp.** { *; }

# Preserve Android support libraries
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-dontwarn androidx.**

# Preserve Android lifecycle components
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

# General Android rules
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn android.arch.**
-dontwarn android.lifecycle.**