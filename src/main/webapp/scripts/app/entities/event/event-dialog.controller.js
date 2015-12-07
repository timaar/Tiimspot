'use strict';

angular.module('tiimspotApp').controller('EventDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Event', 'Adres', 'PersoonEvent', 'Comment',
        function($scope, $stateParams, $modalInstance, $q, entity, Event, Adres, PersoonEvent, Comment) {

        $scope.event = entity;
        $scope.adress = Adres.query({filter: 'event-is-null'});
        $q.all([$scope.event.$promise, $scope.adress.$promise]).then(function() {
            if (!$scope.event.adres || !$scope.event.adres.id) {
                return $q.reject();
            }
            return Adres.get({id : $scope.event.adres.id}).$promise;
        }).then(function(adres) {
            $scope.adress.push(adres);
        });
        $scope.persoonevents = PersoonEvent.query();
        $scope.comments = Comment.query();
        $scope.load = function(id) {
            Event.get({id : id}, function(result) {
                $scope.event = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:eventUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.event.id != null) {
                Event.update($scope.event, onSaveSuccess, onSaveError);
            } else {
                Event.save($scope.event, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
