


Api.getEvents().getEvent('Loaded').subscribe(() => {
  console.log('Hello, World');

  const block = Api.getDefaultLevel().getBlock(Locations.fromVector3({x: 0, y: 0, z: 0}))
  console.log(block.getId());
});