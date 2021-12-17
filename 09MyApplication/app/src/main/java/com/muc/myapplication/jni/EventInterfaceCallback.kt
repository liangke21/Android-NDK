package com.muc.myapplication.jni

import com.muc.myapplication.interfaceCall.NativeInterfaceCallback
import com.muc.myapplication.interfaceCall.NativeThreadInterfaceCallback

class EventInterfaceCallback {


    external fun eventInterfaceCallback(nativeInterfaceCallback: NativeInterfaceCallback)

    external fun eventThreadInterfaceCallback(nativeThreadInterfaceCallback: NativeThreadInterfaceCallback)
}