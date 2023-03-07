# 保留Activity和Application類
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application

# 保留Fragment類
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment

# 保留Gson類
-keep class com.google.gson.** { *; }

# 保留OkHttp類
-keep class okhttp3.** { *; }

# 保留Retrofit類
-keep class retrofit2.** { *; }

# 保留ButterKnife類
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }

# 保留Support Library類
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

# 保留Support Design Library類
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
# RxJava 2
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
-keepclassmembers class io.reactivex.** { *; }

# 保留RxJava 2中的一些接口
-keep interface io.reactivex.** { *; }

# 保留RxJava 2中的一些枚舉
-keepclassmembers enum io.reactivex.** { *; }

# 保留RxJava 2中的一些注解
-keepclassmembers class io.reactivex.android.** { *; }
-keepattributes Signature,Annotation
# RxAndroid 2
-dontwarn com.jakewharton.**
-keep class com.jakewharton.** { *; }
-keepattributes Signature