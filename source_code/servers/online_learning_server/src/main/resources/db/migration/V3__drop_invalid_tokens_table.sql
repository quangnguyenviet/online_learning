-- Remove DB-based token blacklist table after migrating to Redis blacklist
DROP TABLE IF EXISTS invalid_tokens;
