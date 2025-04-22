export {};


declare global {
  var Api: ApiRoot;

  class ApiRoot {
    private constructor();
  
    public getEvents(): EventBus;
    public getLevel(name: string): Level;
    public getServer(): Server;
    public getDefaultLevel(): Level;
    
    public getInteractionActionEnum(): PlayerInteractActionEnum;
  }

  class Server {
    private constructor();

    public executeCommand(sender: CommandSender, command: string): number;
  }
  
  class EventBus {
    private constructor();
  
    public getEvent<T extends keyof EventType>(event: T): EventSignal<T>;
  }
  
  class EventSignal<T extends keyof EventType> {
    private constructor();
  
    public subscribe(callback: (data: EventType[T]) => void): any;
  }
  
  class PlayerChatEventInfo {
    private constructor();
  
    public message: string;
    public player: Player;
  }

  class RegistriesInfo {
    private constructor();

    public registerItem(typeId: string, def: ItemDefinition): void;
    public registerBlock(typeId: string, def: BlockDefinition): void;
  }

  class BlockBreakInfo {
    private constructor();

    public getPlayer(): Player;
    public getBlock(): Block;
    public getItem(): Item;
  }

  class BlockPlaceInfo {
    private constructor();

    public getPlayer(): Player;
    public getBlock(): Block;
  }

  class PlayerInteractInfo {
    private constructor();

    public getPlayer(): Player;
    public getAction(): PlayerInteractActionEnum;
    public getItem(): Item;
    public getBlock(): Block | undefined;
  }

  interface PlayerInteractActionEnum {
    LEFT_CLICK_BLOCK: "LEFT_CLICK_BLOCK";
    LEFT_CLICK_AIR: "LEFT_CLICK_AIR";
    RIGHT_CLICK_BLOCK: "RIGHT_CLICK_BLOCK";
    RIGHT_CLICK_AIR: "RIGHT_CLICK_AIR";
    PHYSICAL: "PHYSICAL";
  }

  class NullEvent {
    private constructor();
  }

  class EntityDamagedByEntityInfo {
    private constructor();

    public getEntity(): Entity;
    public getDamager(): Entity;
    public getDamage(): number;
  }

  class EntityDamagedByBlockInfo {
    private constructor();

    public getEntity(): Entity;
    public getDamager(): Block;
    public getDamage(): number;
  }

  class EntityDamageInfo {
    private constructor();

    public getEntity(): Entity;
    public getDamage(): number;
  }

  interface EventType {
    PlayerChat: PlayerChatEventInfo,
    Registries: RegistriesInfo,
    BlockBreak: BlockBreakInfo,
    BlockPlace: BlockPlaceInfo,
    PlayerInteract: PlayerInteractInfo,
    EntityDamagedByEntity: EntityDamagedByEntityInfo,
    EntityDamagedByBlock: EntityDamagedByBlockInfo,
    EntityDamage: EntityDamageInfo,
    Loaded: NullEvent,
    Unload: NullEvent
  }

  interface ItemDefinition {
    isAxe?: boolean;
    isPickaxe?: boolean;
    isShovel?: boolean;
    isSword?: boolean;
    isHoe?: boolean;
    name?: string;
    creativeCategory?: keyof CreativeCategoryEnum;
    texture?: string;
    itemType?: keyof ItemTypeEnum;
  }

  interface BlockDefinition {
    geometry: string;
    materials: Record<string, BlockMaterialDefinition>;
    breakTime: number;
    blockType?: keyof BlockTypeEnum;
    states?: BlockStateDefinition
  }

  interface CreativeCategoryEnum {
    None: null,
    Equipment: null,
    Items: null,
    Construction: null,
    Nature: null
  }

  interface ItemTypeEnum {
    Item: null,
    Tool: null,
    Armor: null
  }

  interface BlockTypeEnum {
    Solid: null,
    Transparent: null
  }

  interface BlockMaterialDefinition {
    texture: string,
    material: string
  }

  interface BlockStateDefinition {
    typeId: string;
    rangeStart: number;
    rangeEnd: number;
  }
  
  interface Vector3 {
    x: number,
    y: number,
    z: number
  }

  class Location implements Vector3 {
    private constructor();

    public getX(): number;
    public getY(): number;
    public getZ(): number;
    public getChunkX(): number;
    public getChunkY(): number;
    public getChunkZ(): number;
  }

  class Level {
    private constructor();

    public getBlock(pos: Location): Block;
    public setBlock(pos: Location, block: Block): void;
    public loadChunk(x: number, z: number): void
    public addParticleEffect(location: Location, particle: ParticleEffect): void
  }
  
  class Block {
    public static static: BlockStatic;

    private constructor();

    public getLocation(): Location;
    public getId(): string;
    public getLevel(): Level;
    public getLevelBlockEntity(): BlockEntity | undefined;
    public getBlockEntity?(): BlockEntity | null;
    public getOrCreateBlockEntity?(): BlockEntity;
  }

  class BlockStatic {
    public static get(typeId: string): Block;
  }

  class BlockEntity {
    private constructor();

    public getInventory?(): Inventory;
  }
  
  // Entiteis Begin
  
  class Entity {
    private constructor();
  
    public getLocation(): Location;
    public getInventory?(): Inventory;
    public setMotion(vec: Location): void;
    public attack(damage: number): void;
  }
  
  class Player extends Entity {
    private constructor();
  
    public getNeme(): string;
    public getNameTag(): string;
  }

  class Inventory {
    private constructor();

    public getItem(slot: number): Item;
    public getItemInHand?(): Item;
    public setItem(slot: number, item: Item): void;
    public setItemInHand?(item: Item): void;
    public getSize(): number;
  }

  class Item {
    public static static: ItemStatic;
    
    private constructor();

    public getId(): string;

    public clone(): Item;
  }

  class ItemStatic {
    public static get(typeId: string): Item;
  }


  class ParticleEffect {
    private constructor();

    public static static: typeof ParticleEffectStatic
  }

  /**
   * @deprecated Not working
   */
  enum ParticleEffectStatic {
    ARROWSPELL,
    BALLOON_GAS,
    BASIC_BUBBLE,
    BASIC_BUBBLE_MANUAL,
    BASIC_CRIT,
    BASIC_FLAME,
    BASIC_PORTAL,
    BASIC_SMOKE,
    BLEACH,
    BLOCK_DESTRUCT,
    BLOCK_SLIDE,
    BLUE_FLAME,
    CROP_GROWTH,
    CANDLE_FLAME,
    CROP_GROWTH_AREA,
    BREAKING_ITEM_ICON,
    BREAKING_ITEM_TERRAIN,
    BUBBLE_COLUMN_BUBBLE,
    BUBBLE_COLUMN_DOWN,
    BUBBLE_COLUMN_UP,
    CAMERA_SHOOT_EXPLOSION,
    CAMPFIRE_SMOKE,
    CAMPFIRE_SMOKE_TALL,
    CAULDRONSPELL,
    CAULDRON_BUBBLE,
    CAULDRON_SPLASH,
    COLORED_FLAME,
    CONDUIT,
    CONDUIT_ABSORB,
    CONDUIT_ATTACK,
    CRITICAL_HIT,
    DOLPHIN_MOVE,
    DRAGON_BREATH_FIRE,
    DRAGON_BREATH_LINGERING,
    DRAGON_BREATH_TRAIL,
    DRAGON_DEATH_EXPLOSION,
    DRAGON_DESTROY_BLOCK,
    DRAGON_DYING_EXPLOSION,
    DRIPSTONE_LAVA_DRIP,
    DRIPSTONE_WATER_DRIP,
    ELECTRIC_SPARK,
    ENCHANTING_TABLE_PARTICLE,
    ENDROD,
    END_CHEST,
    EVAPORATION_ELEPHANT_TOOTHPASTE,
    EVOCATION_FANG,
    EVOKER_SPELL,
    EXPLOSION_CAULDRON,
    EXPLOSION_DEATH,
    EXPLOSION_EGG_DESTROY,
    EXPLOSION_EYEOFENDER_DEATH,
    EXPLOSION_LABTABLE_FIRE,
    EXPLOSION_LEVEL,
    EXPLOSION_MANUAL,
    EYE_OF_ENDER_BUBBLE,
    FALLING_BORDER_DUST,
    FALLING_DUST,
    FALLING_DUST_CONCRETE_POWDER,
    FALLING_DUST_DRAGON_EGG,
    FALLING_DUST_GRAVEL,
    FALLING_DUST_RED_SAND,
    FALLING_DUST_SAND,
    FALLING_DUST_SCAFFOLDING,
    FALLING_DUST_TOP_SNOW,
    FISH_HOOK,
    FISH_POS,
    GLOW,
    GUARDIAN_ATTACK,
    GUARDIAN_WATER_MOVE,
    HEART,
    HONEY_DRIP,
    HUGE_EXPLOSION_LAB_MISC,
    HUGE_EXPLOSION_LEVEL,
    ICE_EVAPORATION,
    INK,
    KNOCKBACK_ROAR,
    LAB_TABLE_HEATBLOCK_DUST,
    LAB_TABLE_MISC_MYSTICAL,
    LARGE_EXPLOSION_LEVEL,
    LAVA_DRIP,
    LAVA_PARTICLE,
    LLAMA_SPIT,
    MAGNESIUM_SALTS,
    MOBFLAME,
    MOBFLAME_SINGLE,
    MOBSPELL,
    MOBSPELL_AMBIENT,
    MOB_BLOCK_SPAWN,
    MOB_PORTAL,
    MYCELIUM_DUST,
    NECTAR_DRIP,
    NOTE,
    OBSIDIAN_GLOW_DUST,
    OBSIDIAN_TEAR,
    PHANTOM_TRAIL,
    PORTAL_DIRECTIONAL,
    PORTAL_EAST_WEST,
    PORTAL_NORTH_SOUTH,
    PORTAL_REVERSE,
    RAIN_SPLASH,
    REDSTONE_ORE_DUST,
    REDSTONE_REPEATER_DUST,
    REDSTONE_TORCH_DUST,
    REDSTONE_WIRE_DUST,
    RISING_BORDER_DUST,
    SCULK_CHARGE,
    SCULK_CHARGE_POP,
    SCULK_SENSOR_REDSTONE_PARTICLE,
    SCULK_SOUL,
    SHRIEK,
    SHULKER_BULLET,
    SILVERFISH_GRIEF,
    SNOWFLAKE,
    SOUL,
    SONIC_EXPLOSION,
    SPARKLER,
    SPLASHPOTIONSPELL,
    SPONGE_ABSORB_BUBBLE,
    SPORE_BLOSSOM_AMBIENT_BLOCK_ACTOR,
    SPORE_BLOSSOM_SHOWER,
    SQUID_FLEE,
    SQUID_INK_BUBBLE,
    SQUID_MOVE,
    STUNNED,
    TOTEM,
    TOTEM_MANUAL,
    UNDERWATER_TORCH_BUBBLE,
    VIBRATION_SIGNAL,
    VILLAGER_ANGRY,
    VILLAGER_HAPPY,
    WARDEN_DIG_PARTICLE,
    WATER_DRIP,
    WATER_EVAPORATION_ACTOR,
    WATER_EVAPORATION_BUCKET,
    WATER_EVAPORATION_MANUAL,
    WATER_SPASH_MANUAL,
    WATER_SPLASH,
    WATER_WAKE,
    WAX,
    WITHER_BOSS_INVULNERABLE
  }


  //

  class Locations {
    private constructor();

    public static fromVector3(vec: Vector3): Location;
  }

  class Commands {
    private constructor();

    public static getCommandSenderAsAt(entity: Entity, location: Location): CommandSender;
    public static getConsoleCommandSender(): CommandSender;
  }

  class CommandSender {
    private constructor();
  }

  /**
   * @deprecated
   */
  class Java {
    public static type(t: string): unknown;
  }

  class ClassUtil {
    private constructor();
    public forName(name: string): JavaClass & unknown;
  }

  export const Class: ClassUtil;

  class JavaClass {
    public static: any;
  }
}