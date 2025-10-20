# kuiklyMultiModuleDemo

## 项目概述

kuiklyMultiModuleDemo 是一个基于 Kuikly 框架的跨平台多模块架构演示项目，支持 Android、iOS 和 HarmonyOS 三大平台。项目采用 Kotlin Multiplatform 技术栈，实现了代码共享和平台特定的优化。

## 架构设计

### 1. 整体架构

```
┌─────────────────────────────────────────────────────────────┐
│                    跨平台多模块架构                           │
├─────────────────────────────────────────────────────────────┤
│  Platform Layer (平台层)                                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │ Android App │  │   iOS App   │  │  HarmonyOS  │         │
│  │             │  │             │  │    App      │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│  Shared Layer (共享层)                                      │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                   Shared Module                         │ │
│  │  - 主模块，负责模块协调和路由                              │ │
│  │  - 集成所有业务模块                                       │ │
│  └─────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│  Business Layer (业务层)                                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │ moduleAccount│  │moduleMessage│  │ moduleMine  │         │
│  │  账户模块    │  │  消息模块    │  │  个人中心   │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
├─────────────────────────────────────────────────────────────┤
│  Foundation Layer (基础层)                                  │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                 libraryBase                             │ │
│  │  - 基础组件和工具类                                       │ │
│  │  - 通用业务逻辑                                          │ │
│  │  - 平台抽象层                                            │ │
│  └─────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────┤
│  Framework Layer (框架层)                                   │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │                 Kuikly Framework                        │ │
│  │  - 跨平台 UI 渲染引擎                                     │ │
│  │  - 组件系统                                              │ │
│  │  - 状态管理                                              │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### 2. 模块依赖关系

```
Platform Apps (androidApp, iosApp, ohosApp)
    ↓
Shared Module (shared)
    ↓
Business Modules (moduleAccount, moduleMessage, moduleMine)
    ↓
Foundation Module (libraryBase)
    ↓
Kuikly Framework
```

### 3. 技术栈

#### 核心技术
- **Kotlin Multiplatform**: 跨平台代码共享
- **Kuikly Framework**: 腾讯开源的跨平台 UI 框架
- **Gradle**: 构建系统
- **KSP (Kotlin Symbol Processing)**: 代码生成

#### 平台特定技术
- **Android**: Kotlin + Android SDK
- **iOS**: Swift + CocoaPods + Objective-C 桥接
- **HarmonyOS**: ArkTS + Native 开发

### 4. 模块详细说明

#### 4.1 Shared Module (主模块)
- **职责**: 作为主模块，负责整合所有业务模块
- **配置**: `isMainModule = true`
- **子模块**: `moduleMine&moduleMessage&moduleAccount`
- **平台支持**: Android, iOS, HarmonyOS

#### 4.2 Business Modules (业务模块)

##### moduleAccount (账户模块)
- **命名空间**: `com.susir.account.moduleAccount`
- **职责**: 用户账户管理、登录注册等功能
- **依赖**: libraryBase

##### moduleMessage (消息模块)
- **命名空间**: `com.susir.message.moduleMessage`
- **职责**: 消息处理、通知管理等功能
- **依赖**: libraryBase

##### moduleMine (个人中心模块)
- **命名空间**: `com.susir.mine.moduleMine`
- **职责**: 个人信息、设置等功能
- **依赖**: libraryBase

#### 4.3 libraryBase (基础模块)
- **命名空间**: `com.susir.librarybase.libraryBase`
- **职责**: 
  - 提供基础组件和工具类
  - 封装通用业务逻辑
  - 定义平台抽象接口
- **配置**: `isMainModule = false`

### 5. 跨平台配置

#### 5.1 版本管理
```kotlin
// Kuikly 版本配置
KUIKLY_VERSION = "2.1.1"
KOTLIN_VERSION = "1.9.22"
KOTLIN_OHOS_VERSION = "2.0.21-ohos"

// 版本号规则
Android/iOS: ${KUIKLY_VERSION}-${KOTLIN_VERSION}
HarmonyOS: ${KUIKLY_VERSION}-${KOTLIN_OHOS_VERSION}
```

#### 5.2 平台特定配置

##### Android 配置
- **编译 SDK**: 34
- **最小 SDK**: 21
- **目标 SDK**: 30
- **JVM 目标**: 1.8

##### iOS 配置
- **部署目标**: iOS 14.1+
- **架构支持**: x64, arm64, simulatorArm64
- **CocoaPods**: 依赖管理
- **框架**: OpenKuiklyIOSRender 2.1.1

##### HarmonyOS 配置
- **目标架构**: ohosArm64
- **构建文件**: build.ohos.gradle.kts
- **包管理**: oh-package.json5

### 6. 构建系统

#### 6.1 多平台构建配置
```kotlin
// settings.gradle.kts - 标准构建
include(":androidApp", ":shared", ":libraryBase", 
        ":moduleMine", ":moduleMessage", ":moduleAccount")

// settings.ohos.gradle.kts - HarmonyOS 构建
// 使用 build.ohos.gradle.kts 作为构建文件
```

#### 6.2 KSP 代码生成配置
每个模块都配置了 KSP 参数：
- `moduleId`: 模块标识符
- `isMainModule`: 是否为主模块
- `subModules`: 子模块列表（用&分隔）
- `enableMultiModule`: 启用多模块支持

### 7. 开发规范

#### 7.1 模块开发原则
1. **单一职责**: 每个模块专注于特定的业务领域
2. **依赖倒置**: 业务模块依赖基础模块，不直接依赖其他业务模块
3. **接口隔离**: 通过接口定义模块间的通信协议
4. **开闭原则**: 模块对扩展开放，对修改封闭

#### 7.2 跨平台开发注意事项
1. **避免平台特定代码**: 在 commonMain 中不使用平台特定的 API
2. **使用 expect/actual**: 对于平台特定实现，使用 expect/actual 机制
3. **资源管理**: 将共享资源放在 commonMain/assets 目录
4. **测试策略**: 为每个平台编写对应的测试用例

### 8. 项目结构

```
kuiklyMultiModuleDemo/
├── androidApp/                 # Android 应用
├── iosApp/                     # iOS 应用
├── ohosApp/                    # HarmonyOS 应用
├── shared/                     # 主共享模块
├── libraryBase/                # 基础库模块
├── moduleAccount/              # 账户业务模块
├── moduleMessage/              # 消息业务模块
├── moduleMine/                 # 个人中心业务模块
├── buildSrc/                   # 构建脚本
├── gradle/                     # Gradle 配置
├── build.gradle.kts            # 根构建文件
├── build.ohos.gradle.kts       # HarmonyOS 构建文件
├── settings.gradle.kts         # 项目设置
└── settings.ohos.gradle.kts    # HarmonyOS 项目设置
```

### 9. 快速开始

#### 9.1 环境要求
- JDK 8+
- Android Studio Arctic Fox+
- Xcode 13+ (iOS 开发)
- DevEco Studio (HarmonyOS 开发)

#### 9.2 构建命令
```bash
# Android 构建
./gradlew :androidApp:assembleDebug

# iOS 构建
cd iosApp && xcodebuild

# HarmonyOS 构建
./gradlew -c settings.ohos.gradle.kts build
```

### 10. 扩展指南

#### 10.1 添加新业务模块
1. 创建新模块目录
2. 配置 build.gradle.kts 和 build.ohos.gradle.kts
3. 在 settings 文件中注册模块
4. 在 shared 模块中添加依赖
5. 配置 KSP 参数