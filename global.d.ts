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
  
    public subscribe(callback: (data: EventType[T]) => void);
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

  interface EventType {
    PlayerChat: PlayerChatEventInfo
  }

  class Level {
    private constructor();

    public getBlock(pos: Vector3): Block;
  }
  
  class Block {
    private constructor();

    public getLocation(): Vector3;
    public getTypeId(): string;
  }
  
  // Entiteis Begin
  
  class Entity {
    private constructor();
  
    public getLocation(): Vector3;
  }
  
  class Player extends Entity {
    private constructor();
  
    public getNeme(): string;
    public getNameTag(): string;
  }
}