CREATE TABLE logs (
  id SERIAL PRIMARY KEY,
  ip text,
  waypoint text,
  query text,
  queryTime TIMESTAMP
);