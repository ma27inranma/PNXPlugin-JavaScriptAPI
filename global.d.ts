export {};


declare global {
  var Api: ApiRoot;

  class ApiRoot {
    private constructor();
  
    public getEvents(): EventBus;
    public getLevel(name: string): Level;
    public getDefaultLevel(): Level;
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

  interface EventType {
    PlayerChat: PlayerChatEventInfo
  }

  class Level {
    private constructor();

    public getBlock(pos: Location): Block;
  }
  
  class Block {
    private constructor();

    public getLocation(): Location;
    public getId(): string;
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
    public setItemInHand(item: Item): void;
  }

  class Item {
    private constructor();

    public getTypeId(): string;

    public clone(): Item;
  }


  //

  class Locations {
    private constructor();

    public static fromVector3(vec: Vector3): Location;
  }
}