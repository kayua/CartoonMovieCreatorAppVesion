package app.mosquito.appmosquito.appmosquito.Posts.BarFollower;


public enum FollowerState {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
