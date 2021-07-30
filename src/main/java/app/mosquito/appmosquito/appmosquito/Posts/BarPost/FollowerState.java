package app.mosquito.appmosquito.appmosquito.Posts.BarPost;


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
