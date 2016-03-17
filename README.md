# BroadcastDome
BroadcastReceiver（广播接收器）是Android中的四大组件之一，专注于接收广播通知信息，并做出对应处理的组件。

广播接收主要有两种，一种是用户发送的广播，另外一种是系统的广播消息的接收，包括用户来电、用户短信和拦截黑名单电话等。这里我们所要说的广播接收是监听我们自己注册的普通广播的一个接收。

使用广播接收需完成：
        （1）注册广播：注册方式有两种，一种是在AndroidManifest.xml文件中定义，指定该BroadcastReceiver所响应的Intent的Action；另一种是使用代码进行指定，调用BroadcastReceiver的Context的registerReceiver(BroadcastReceiver receiver, IntentFilter filter)方法指定；这两种注册方式都需要IntentFIlter。
        （2）发送广播：调用Context的sendBroadcast来发发送指定的BroadcastReceiver，也就是使用Intent来传递注册时用到的Action。
        （3）接收广播：当发送的广播被广播接收器监听到之后，BroadcastReceiver的onReceive()方法将会被触发，从而在该方法中显示广播所携带的信息或者进行一些简单的操作。
![ABC](http://img.blog.csdn.net/20160121005240767?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
