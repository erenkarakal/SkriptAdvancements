package me.eren.skriptadvancements.elements.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.eren.skriptadvancements.AdvancementTabBuilder;
import me.eren.skriptadvancements.AdvancementUtils;
import me.eren.skriptadvancements.wrapper.BaseAdvancementWrapper;
import me.eren.skriptadvancements.wrapper.RootAdvancementWrapper;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;

import java.util.List;

@SuppressWarnings("unchecked")
public class SecAdvancement extends Section {

    private static final EntryValidator.EntryValidatorBuilder ENTRY_VALIDATOR = EntryValidator.builder();

    private Expression<String> key, title, description, backgroundPath, parent;
    private Expression<ItemType> icon, background;
    private Expression<AdvancementFrameType> frameType;
    private Expression<Boolean> showToast, announceToChat;
    private Expression<Number> x, y, maxProgression;
    private boolean isRoot;

    static {
        Skript.registerSection(SecAdvancement.class, "(create|register) a new [:root] advancement");

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

        if (isRoot && container.getOptional("background", false) == null && container.getOptional("background path", false) == null) {
            Skript.error("Root advancement must have either 'background' or 'background path' entry");
            return false;
        }
        if (!isRoot && container.getOptional("parent", false) == null) {
            Skript.error("Non-root advancements must have a 'parent' entry");
            return false;
        }

        parent = (Expression<String>) container.getOptional("parent", false);
        key = (Expression<String>) container.getOptional("key", false);
        description = (Expression<String>) container.getOptional("description", false);
        title = (Expression<String>) container.getOptional("title", false);
        backgroundPath = (Expression<String>) container.getOptional("background path", false);

        icon = (Expression<ItemType>) container.getOptional("icon", false);
        background = (Expression<ItemType>) container.getOptional("background", false);

        showToast = (Expression<Boolean>) container.getOptional("show toast", false);
        announceToChat = (Expression<Boolean>) container.getOptional("announce completion", false);

        frameType = (Expression<AdvancementFrameType>) container.getOptional("frame type", false);

        x = (Expression<Number>) container.getOptional("x", false);
        y = (Expression<Number>) container.getOptional("y", false);
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
        Number maxProgression = this.maxProgression == null ? 1 : this.maxProgression.getSingle(event);

        AdvancementDisplay display = new AdvancementDisplay(icon.getMaterial(), title, frameType, showToast, announceToChat, x.floatValue(), y.floatValue(), description);

        AdvancementTabBuilder tab = SecAdvancementTab.lastCreatedTab;
        if (isRoot) {
            RootAdvancementWrapper root = new RootAdvancementWrapper(key, display, backgroundPath, Math.min(maxProgression.intValue(), 1));
            tab.setRoot(root);
        } else {
            tab.addAdvancement(key, new BaseAdvancementWrapper(key, display, parent, Math.min(maxProgression.intValue(), 1)));
        }
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "register a new " + (isRoot ? "root " : "") + "advancement";
    }

}
