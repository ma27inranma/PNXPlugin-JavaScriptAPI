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

  interface EventType {
    PlayerChat: PlayerChatEventInfo,
    Registries: RegistriesInfo,
    BlockBreak: BlockBreakInfo,
    BlockPlace: BlockPlaceInfo,
    PlayerInteract: PlayeriNteract
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
  }
  
  class Block {
    public static get(typeId: string): Block;

    private constructor();

    public getLocation(): Location;
    public getId(): string;
    public getLevel(): Level;
    public getLevelBlockEntity(): BlockEntity | undefined;
    public getBlockEntity?(): BlockEntity | null;
    public getOrCreateBlockEntity?(): BlockEntity;
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
    public static get(typeId: string): Item;

    private constructor();

    public getId(): string;

    public clone(): Item;
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
}