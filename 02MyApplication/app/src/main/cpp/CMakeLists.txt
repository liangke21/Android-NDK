# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
#��Ͱ汾
cmake_minimum_required(VERSION 3.4.1)

# Declares and names the project.
project("a02myapplication")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# CMake 貌似不支持中文

#设置变量
set(var hello)
#打印变量
message(${var})
#给变量重新赋值
set(var 111)
message(${var})

#打印常量
message(${CMAKE_CURRENT_LIST_FILE})
message(${CMAKE_CURRENT_LIST_DIR})

#if 语句

IF (TRUE)
    message("this CMake")
ENDIF ()

add_library( # Sets the name of the library.
        a02myapplication

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)

add_library(
        people-lib
        SHARED
        people/People.cpp
        )



# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

#这样可以通过<> 来查找   一开始没有成功 我是重启了idea 才成功的
include_directories(people/)


find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        a02myapplication
        people-lib
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})