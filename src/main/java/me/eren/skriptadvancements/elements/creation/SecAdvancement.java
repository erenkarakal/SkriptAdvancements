package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.eren.skriptadvancements.AdvancementUtils;
import me.eren.skriptadvancements.SkriptAdvancements;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class SecAdvancement extends EffectSection {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    private Expression<String> keyExpression, titleExpression, descriptionExpression, backgroundPathExpression, parentExpression;
    private Expression<ItemType> iconExpression, backgroundExpression;
    private Expression<AdvancementFrameType> frameTypeExpression;
    private Expression<Boolean> showToastExpression, announceToChatExpression;
    private Expression<Double> xExpression, yExpression;
    private Expression<Long> maxProgressionExpression;
    private boolean isRoot;

    static {
        Skript.registerSection(SecAdvancement.class, "(create|register) a new [(:root)] advancement");

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("parent", null, true, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("key", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("icon", null, false, ItemType.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("title", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("description", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("frame type", null, false, AdvancementFrameType.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("show toast", null, false, boolean.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("announce to chat", null, false, boolean.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("x", null, false, double.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("y", null, false, double.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("background", null, true, ItemType.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("background path", null, true, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("max progression", null, true, long.class));
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed,
                        SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        EntryContainer container = ENTRY_VALIDATOR.build().validate(sectionNode);
        if (container == null) return false;
        isRoot = parseResult.hasTag("root");

        if (container.getOptional("background", false) == null && container.getOptional("background path", false) == null) {
            Skript.error("The builder must have either 'background' or 'background path' entry");
            return false;
        }
        if (!isRoot && container.getOptional("parent", false) == null) {
            Skript.error("Non-root advancements must have a 'parent' entry");
            return false;
        }

        parentExpression = (Expression<String>) container.get("parent", false);
        keyExpression = (Expression<String>) container.get("key", false);
        iconExpression = (Expression<ItemType>) container.get("icon", false);
        titleExpression = (Expression<String>) container.get("title", false);
        descriptionExpression = (Expression<String>) container.get("description", false);
        frameTypeExpression = (Expression<AdvancementFrameType>) container.get("frame type", false);
        showToastExpression = (Expression<Boolean>) container.get("show toast", false);
        announceToChatExpression = (Expression<Boolean>) container.get("announce to chat", false);
        xExpression = (Expression<Double>) container.get("x", false);
        yExpression = (Expression<Double>) container.get("y", false);
        backgroundExpression = (Expression<ItemType>) container.get("background", false);
        backgroundPathExpression = (Expression<String>) container.get("background path", false);
        maxProgressionExpression = (Expression<Long>) container.get("max progression", false);
        return true;
    }

    @Override
    protected TriggerItem walk(Event event) {
        String key = Objects.requireNonNull(keyExpression.getSingle(event));
        String title = Objects.requireNonNull(titleExpression.getSingle(event));
        String description = Objects.requireNonNull(descriptionExpression.getSingle(event));
        Material icon = Objects.requireNonNull(iconExpression.getSingle(event)).getMaterial();
        String advancementParent = Objects.requireNonNull(parentExpression.getSingle(event));

        String backgroundPath;
        ItemType backgroundItem = backgroundExpression.getSingle(event);
        if (backgroundItem != null) {
            backgroundPath = AdvancementUtils.getTexture(backgroundItem.getMaterial());
        } else {
            backgroundPath = Objects.requireNonNull(backgroundPathExpression.getSingle(event));
        }

        AdvancementFrameType frameType = Objects.requireNonNull(frameTypeExpression.getSingle(event));
        boolean showToast = Boolean.TRUE.equals(showToastExpression.getSingle(event));
        boolean announceToChat = Boolean.TRUE.equals(announceToChatExpression.getSingle(event));
        float x = Objects.requireNonNull(xExpression.getSingle(event)).floatValue();
        float y = Objects.requireNonNull(yExpression.getSingle(event)).floatValue();
        int maxProgression = Objects.requireNonNull(maxProgressionExpression.getSingle(event)).intValue();

        Advancement advancement;
        AdvancementDisplay display = new AdvancementDisplay(icon, title, frameType, showToast, announceToChat, x, y, description);
        if (isRoot) {
            AdvancementTab tab = SecRegisterTab.lastCreatedTab.tab;
            if (maxProgression > 0) {
                advancement = new RootAdvancement(tab, key, display, backgroundPath, maxProgression);
            } else {
                advancement = new RootAdvancement(tab, key, display, backgroundPath);
            }
            SecRegisterTab.lastCreatedTab.rootAdvancement = (RootAdvancement) advancement;
        } else {
            Advancement parentAdvancement = Objects.requireNonNull(SkriptAdvancements.getAdvancementAPI().getAdvancement(advancementParent));
            if (maxProgression > 0) {
                advancement = new BaseAdvancement(key, display, parentAdvancement, maxProgression);
            } else {
                advancement = new BaseAdvancement(key, display, parentAdvancement);
            }
            SecRegisterTab.lastCreatedTab.advancements.add((BaseAdvancement) advancement);
        }

        return getNext();
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "register a new advancement";
    }

}
