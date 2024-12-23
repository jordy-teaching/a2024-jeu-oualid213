package Warriors91I.Utilities;

    public class Chronometre {
        private long startTime;
        private long stopTime;
        private boolean running;

        public Chronometre() {
            reset();
        }

        public void start() {
            if (!running) {
                startTime = System.nanoTime();
                running = true;
            }
        }

        public void stop() {
            if (running) {
                stopTime = System.nanoTime();
                running = false;
            }
        }

        public void reset() {
            startTime = 0;
            stopTime = 0;
            running = false;
        }

        public long getElapsedTimeInSeconds() {
            long endTime = running ? System.nanoTime() : stopTime;
            return (endTime - startTime) / 1_000_000_000;
        }

        public long getElapsedTimeInMillis() {
            long endTime = running ? System.nanoTime() : stopTime;
            return (endTime - startTime) / 1_000_000;
        }

        public boolean isRunning() {
            return running;
        }
    }

