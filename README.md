# Base-knowledge

阅读源码的一些收获，详细的内容在注释中

- 算法
- 各种Map、Set、List知识点，优缺点....
- 实现Collection:Set、List `com.eddie.container.collection`
- 线程安全的Map实现类 `com.eddie.container.safe`
- 非线程安全的Map实现类 `com.eddie.container.unsafe`
[详细介绍](https://radiancel.github.io/2018/07/30/Map-Set-List/)

- Stream、Lambda表达式`com.eddie.stream`
- 动态代理 `com.eddie.proxy`
- 数据结构 `com.eddie.structure`

## 算法

目前LetCode中文版本已经发布，一些算法相关内容都会在`com.eddie.algorithm`
包下

## 容器

Java拥有非常多的自带数据结构，在我们实现自己模拟数据结构之前，阅读源码是一个非常棒的选择。
阅读源码主要针对的内容还是以线程安全以及效率的问题，容器的特性方面在自己实现容器的时候再详细看
这些容器相关内容在`com.eddie.container`包下

## Java特性及基础知识

目前我本地Jdk版本是11，
综合使用的api和特性均是Java 8，这类实现在`com.eddie.proxy`、`com.eddie.stream`、`com.eddie.thread`

## 自定义数据结构

这部分内容来自慕课网`liuyubobobo`老师，个人认为，老师讲的非常好，而且富有耐心，对数据结构理解透彻，讲课一针见血能够说到点上
这个包下（`com.eddie.structure`），均是看视频后自己练习代码。

后续算法部分也会更新`liuyubobobo`老师的算法课相关内容
虽然我这里写了一些老师的代码，但这几门课讲的非常好，大家感兴趣可以去慕课网搜索，付费课程

## Spring 注解

注解学习记录，一些简单的注解就不放在这继续多浪费时间了，第一个很重要的注解是条件注解`Condition`
相关的代码在`com.eddie.conditional`包下，没有建单独的Spring目录，等后续在发现什么好玩的注解再汇总吧

## 另外
- 算法相关内容已经转移到另外一个[地址](https://github.com/RadianceL/algorithm-java-common)
- [个人博客地址](https://radiancel.github.io)
