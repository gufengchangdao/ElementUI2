# ElementUI2

一代地址：[ElementUI](https://github.com/gufengchangdao/ElementUI)

项目网址：[ElementUI2](https://github.com/gufengchangdao/ElementUI2)

图标下载：[icon2](https://n77a3mjegs.feishu.cn/file/boxcn6DBZiLZ0qFneZTEgHJ29mb)

飞书文档：[ElementUI2](https://n77a3mjegs.feishu.cn/docx/C7bYdinwZoS8QVx7FfXcyvBLnIc)

## 描述

该项目是一代的升级版本，主要有以下特点

- 该项目使用jdk17。(主要是我想用了(･ิω･ิ))
- 该项目整合其他优秀项目和框架，汲取其经验。我自知自己经验不足，自己的代码可能会存在不少不足之处，所以把开源的高质量项目拿来学习并整合(删减、优化、升级)到自己项目中去。
- 与JIDE开源代码整合，对JIDE源代码删改和优化
- 利用整合后的类优化element样式的组件，项目内原代码进行了删改，并由于内容的增加重新进行了排版

## 参考的项目(包括一代里的)

- [radiance](https://github.com/kirill-grouchnikov/radiance)
  很棒的框架，我把SVG转图标类的功能从里面提取了出来，并加以改进，减少图标代码量，修复缩放问题。
- [java-swing-tips](https://github.com/aterai/java-swing-tips.git)
  对组件的开发提供了许多思路，让人恍然大悟，原来还可以这么写。并且每个包都是一个示例，不存在依赖关系，容易看懂。提供组件设计思路。
- [jide-oss](https://github.com/jidesoft/jide-oss)
  JIDE的公开层，较为完善的框架，存在许多我不懂的地方，看懂项目、筛选有用代码、修复bug，还有代码优化花了我不少时间，算是把该项目由组件库升级为了一个小框架。

## 开发指南
**代码中使用ElementUI**

一般你的应用启动类这样写：
```java
EventQueue.invokeLater(() -> {
   // 检测swing组件更新的线程违规操作，仅开发时启用
   RepaintManager.setCurrentManager(new ThreadCheckingRepaintManager());
   // 创建计时源并启动
   SwingTimerTimingSource ts = new SwingTimerTimingSource();
   Animator.setDefaultTimingSource(ts);
   ts.init();
   //安装 LookAndFeel
   // FlatLightLaf.setup();
   // 注册jide组件和默认组件的UI，应该在安装 LookAndFeel 后执行
   LookAndFeelFactory.installJideExtension();

   //启动程序
   // ...
});
```

## 截图预览

[ElementUI组件](https://riw8lxejdn.feishu.cn/docx/Yoszdo08qooCAJxOKlQcS4hJnTg#XYMydSEyAogeOixOAzqcTV1ened)

[JIDE的开源组件](https://n77a3mjegs.feishu.cn/docx/C7bYdinwZoS8QVx7FfXcyvBLnIc#L6MgdkQoQoa0qCxyLAPccGEPnDq)

![src\test\resources\img\1.png](src\test\resources\img\1.png)
![src\test\resources\img\1.png](src\test\resources\img\2.png)
![src\test\resources\img\1.png](src\test\resources\img\3.png)