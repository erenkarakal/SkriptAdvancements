package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class SecAdvancement extends Section {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    private Expression<String> key, title, description, backgroundPath, tab, parent;
    private Expression<ItemType> icon, background;
    private Expression<AdvancementFrameType> frameType;
    private Expression<Boolean> showToast, announceToChat;
    private Expression<Number> x, y, maxProgression;
    private boolean isRoot;

    static {
        Skript.registerSection(SecAdvancement.class, "(create|register) a new [:root] advancement");

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("tab", null, true, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("parent", null, true, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("key", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("title", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("description", null, false, String.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("background path", null, true, String.class));

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("icon", null, false, ItemType.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("background", null, true, ItemType.class));

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("show toast", null, false, Boolean.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("announce completion", null, false, Boolean.class));

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("frame type", null, false, AdvancementFrameType.class));

        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("x", null, false, Number.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("y", null, false, Number.class));
        ENTRY_VALIDATOR.addEntryData(new ExpressionEntryData<>("max progression", null, true, Number.class));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
        EntryContainer container = ENTRY_VALIDATOR.build().validate(sectionNode);
        if (container == null) return false;
        isRoot = parseResult.hasTag("root");

        if (container.getOptional("background", false) == null && container.getOptional("background path", false) == null) {
            Skript.error("The builder must have either 'background' or 'background path' entry");
            return false;
        }
        if (isRoot && container.getOptional("tab", false) == null) {
            Skript.error("Root advancements must have a 'tab' entry");
            return false;
        }
        if (!isRoot && container.getOptional("parent", false) == null) {
            Skript.error("Non-root advancements must have a 'parent' entry");
            return false;
        }

        tab = (Expression<String>) container.getOptional("tab", false);
        parent = (Expression<String>) container.getOptional("parent", false);
        key = (Expression<String>) container.getOptional("key", false);
        icon = (Expression<ItemType>) container.getOptional("icon", false);
        title = (Expression<String>) container.getOptional("title", false);
        description = (Expression<String>) container.getOptional("description", false);
        frameType = (Expression<AdvancementFrameType>) container.getOptional("frame type", false);
        showToast = (Expression<Boolean>) container.getOptional("show toast", false);
        announceToChat = (Expression<Boolean>) container.getOptional("announce completion", false);
        x = (Expression<Number>) container.getOptional("x", false);
        y = (Expression<Number>) container.getOptional("y", false);
        background = (Expression<ItemType>) container.getOptional("background", false);
        backgroundPath = (Expression<String>) container.getOptional("background path", false);
        maxProgression = (Expression<Number>) container.getOptional("max progression", false);
        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event event) {
        execute(event);
        return super.walk(event, false);
    }

    @SuppressWarnings("ConstantConditions")
    private void execute(Event event) {
        String key = this.key == null ? null : this.key.getSingle(event);
        String title = this.title == null ? null : this.title.getSingle(event);
        String description = this.description == null ? null : this.description.getSingle(event);
        ItemType icon = this.icon == null ? null : this.icon.getSingle(event);
        String preTab = this.tab == null ? null : this.tab.getSingle(event);
        String parent = this.parent == null ? null : this.parent.getSingle(event);

        String backgroundPath;
        ItemType backgroundItem = this.background == null ? null : this.background.getSingle(event);
        if (backgroundItem != null) {
            backgroundPath = AdvancementUtils.getTexture(backgroundItem.getMaterial());
        } else {
            backgroundPath = this.backgroundPath == null ? null : this.backgroundPath.getSingle(event);
        }

        AdvancementFrameType frameType = this.frameType == null ? null : this.frameType.getSingle(event);
        boolean showToast = Boolean.TRUE.equals(this.showToast.getSingle(event));
        boolean announceToChat = Boolean.TRUE.equals(this.announceToChat.getSingle(event));
        Number x = this.x == null ? null : this.x.getSingle(event);
        Number y = this.y == null ? null : this.y.getSingle(event);
        Number maxProgression = this.maxProgression == null ? null : this.maxProgression.getSingle(event);

        if (Stream.of(key, title, description, icon, backgroundPath, frameType, x, y, maxProgression).anyMatch(Objects::isNull)) {
            Bukkit.getLogger().info("returning due to null element");
            return;
        } else if (preTab == null && isRoot) {
            Bukkit.getLogger().info("returning due to null tab");
            return;
        } else if (parent == null && !isRoot) {
            Bukkit.getLogger().info("returning due to null parent");
            return;
        }

        AdvancementDisplay display = new AdvancementDisplay(icon.getMaterial(), title, frameType, showToast, announceToChat, x.floatValue(), y.floatValue(), description);
        if (isRoot) {
            AdvancementTab tab = SkriptAdvancements.getAdvancementAPI().getAdvancementTab(preTab);
            if (tab != null) {
                RootAdvancement root = new RootAdvancement(tab, key, display, backgroundPath, Math.min(maxProgression.intValue(), 1));
                ExprTabs.TABS.get(preTab).setRoot(root);
            }
        } else {
            Advancement parentAdvancement = SkriptAdvancements.getAdvancementAPI().getAdvancement(parent);
            if (parentAdvancement != null)
                ExprTabs.TABS.get(preTab).addAdvancement(new BaseAdvancement(key, display, parentAdvancement, Math.min(maxProgression.intValue(), 1)));
            else
                Bukkit.getLogger().info("parent advancement was null");
        }
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "register a new " + (isRoot ? "root " : "") + "advancement";
    }

}
