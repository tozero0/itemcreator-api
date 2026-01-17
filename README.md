# MCZJU ItemCreator API

---

本 API 是 `MCZJUItemCreator` 插件对外提供的公共 API，主要功能是由使用方插件通过**物品 ID** 获取由 `MCZJUItemCreator` 配置并管理的自定义物品。

API 当前版本为 `1.0`。

API 只包含接口定义，不包含任何实现逻辑。具体实现由 `MCZJUItemCreator` 插件在运行时通过 Bukkit 的 `ServicesManager` 提供。

---

### 1. 基本功能简述

- 给定一个物品 ID（字符串），向 `MCZJUItemCreator` 请求一个新的 `ItemStack` 实例。
- 检查是否有该 ID 的物品被配置。
- 获取所有物品 ID （用于命令补全等场景）。

---

### 2. 添加依赖

本 API 以独立 Maven 依赖的形式提供。

以 Maven 构建为例，在 `pom.xml` 中添加对 itemcreator-api 的依赖，
同时将该依赖声明为 provided（仅在编译期使用，实际运行时由 `MCZJUItemCreator` 插件提供）即可。

示例：

```xml
<dependency>
  <groupId>io.mczju</groupId>
  <artifactId>itemcreator-api</artifactId>
  <version>1.0</version>
  <scope>provided</scope>
</dependency>
```

---

### 3. plugin.yml 配置

使用方插件在 `plugin.yml` 中声明对 MCZJUItemCreator 的软依赖（softdepend），
以确保在 `MCZJUItemCreator` 存在时优先加载它。

示例：

```yaml
softdepend: [MCZJUItemCreator]
```

---

### 4. 代码中使用 API

使用方插件在 onEnable 阶段，通过 Bukkit 的 `ServicesManager` 获取 `ItemCreatorApi` 实例。

插件主类示例：

```java
import io.mczju.mczjuitemcreator.api.ItemCreatorApi;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MyPlugin extends JavaPlugin {

    private ItemCreatorApi itemCreatorApi;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<ItemCreatorApi> rsp =
                getServer().getServicesManager().getRegistration(ItemCreatorApi.class);

        if (rsp != null) {
            itemCreatorApi = rsp.getProvider();
            getLogger().info("Hooked into MCZJUItemCreator.");
        } else {
            itemCreatorApi = null;
            getLogger().warning("MCZJUItemCreator not found, item features disabled.");
        }
    }
}
```
业务逻辑示例：

```java
ItemStack item = itemCreatorApi
        .createItem("example_item")
        .orElse(null);
```

---

### 5. 补充信息

建议使用方插件在需要时直接请求生成 `ItemStack` 实例，而非先缓存，以免 `MCZJUItemCreator` 插件配置重载后缓存过期。
