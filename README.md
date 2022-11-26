# ElementUI2

一代地址：[ElementUI](https://github.com/gufengchangdao/ElementUI)
项目网址：[ElementUI2](https://github.com/gufengchangdao/ElementUI2)

## 描述

该项目是一代的升级版本，主要有以下特点

- 该项目使用jdk17。(主要是我想用了(･ิω･ิ))
-
该项目整合其他优秀项目和框架，汲取其经验。我自知自己经验不足，自己的代码可能会存在不少不足之处，所以把开源的高质量项目拿来学习并整合(
删减、优化、升级)到自己项目中去。
- 利用整合后的类优化element样式的组件

## 开发难点

1. 基本上都是国外项目，拿来的项目要整合到自己项目中去就得把整个项目都能看懂，知道为什么。
2. 项目里的功能经常与自己已有的功能相重合或类似，需要修改源码、合并等
3. 项目多为N年前的老项目，少有维护，存在低效率代码和废弃代码，需要对代码进行分析并优化代码，这个过程需要小心把对的代码改错了
4. 认为项目中代码存在部分错误，要修改必须小心翼翼，马虎不得，一不留神就可能导致部分功能异常，很难发现。

## 借鉴的项目(包含一代里的)

- [radiance](https://github.com/kirill-grouchnikov/radiance)
  很棒的框架，我把SVG转图标类的功能从里面提取了出来
- [java-swing-tips](https://github.com/aterai/java-swing-tips.git)
  对组件的开发提供了许多思路，让人恍然大悟，原来还可以这么写。并且每个包都是一个示例，不存在依赖关系，容易看懂。
- [jide-oss](https://github.com/jidesoft/jide-oss)
  JIDE的公开层，较为完善的框架，存在许多我不懂的地方，看懂项目、筛选有用代码还有代码优化花了我不少时间