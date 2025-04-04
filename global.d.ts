export {};


declare global {
  var Api: ApiRoot;

  class ApiRoot {
    private constructor();
  
    public getEvents(): EventBus;
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
  
  class Location {
    public constructor(x: number, y: number, z: number);
  
    public x: number;
    public y: number;
    public z: number;
  }

  interface EventType {
    PlayerChat: PlayerChatEventInfo
  }
  
  // Entiteis Begin
  
  class Entity {
    private constructor();
  
    public getLocation(): Location;
  }
  
  class Player extends Entity {
    private constructor();
  
    public getNeme(): string;
    public getNameTag(): string;
  }
}