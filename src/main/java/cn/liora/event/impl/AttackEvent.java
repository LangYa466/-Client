package cn.liora.event.impl;

import cn.liora.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.entity.Entity;

/**
 * @author LangYa466
 * @date 8/6/2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AttackEvent extends Event {
    private final Entity target;
}
