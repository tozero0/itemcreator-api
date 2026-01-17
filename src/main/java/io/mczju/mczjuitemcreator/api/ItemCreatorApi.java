package io.mczju.mczjuitemcreator.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * MCZJUItemCreator 插件对外提供的 API。
 *
 * <p>
 * 功能：
 * <ul>
 *   <li>为其它插件提供通过物品 ID 获取 ItemStack的能力</li>
 *   <li>不暴露 MCZJUItemCreator 的内部实现（如 registry、ItemBuilder 等）</li>
 *   <li>支持在配置 reload 后，使用方安全地感知数据变化</li>
 * </ul>
 *
 * <p>
 * 使用约定：
 * <ul>
 *   <li>所有 {@link ItemStack} 均为“新实例”，修改不会影响内部模板</li>
 *   <li>当物品 ID 不存在时，返回 {@link Optional#empty()}</li>
 *   <li>接口通过 Bukkit {@code ServicesManager} 提供</li>
 * </ul>
 */
public interface ItemCreatorApi {

    /**
     * 检查指定 ID 的物品是否存在。
     *
     * @param id 物品 ID
     * @return 是否存在该物品定义
     */
    boolean hasItem(@NotNull String id);

    /**
     * 根据物品 ID 创建一个新的物品实例。
     *
     * <p>
     * 如果物品不存在，返回 {@link Optional#empty()}。
     *
     * @param id 物品 ID
     * @return 构建完成的 ItemStack（新实例）
     */
    @NotNull
    Optional<ItemStack> createItem(@NotNull String id);

    /**
     * 根据物品 ID 创建一个新的物品实例，并覆盖数量。
     *
     * <p>
     * {@code amountOverride} 的优先级高于配置文件中的 amount。
     * 如果物品不存在，返回 {@link Optional#empty()}。
     *
     * @param id 物品 ID
     * @param amountOverride 覆盖的物品数量
     * @return 构建完成的 ItemStack（新实例）
     */
    @NotNull
    Optional<ItemStack> createItem(@NotNull String id, int amountOverride);

    /**
     * 获取当前已注册的所有物品 ID。
     *
     * <p>
     * 返回集合仅用于只读用途（如 Tab 补全、校验），
     * 不保证可修改。
     *
     * @return 所有已注册的物品 ID
     */
    @NotNull
    Set<String> getAllIds();
}
