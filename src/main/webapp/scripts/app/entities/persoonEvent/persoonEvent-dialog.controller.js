'use strict';

angular.module('tiimspotApp').controller('PersoonEventDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'PersoonEvent', 'EventScore', 'Event', 'Persoon', 'Comment',
        function($scope, $stateParams, $modalInstance, $q, entity, PersoonEvent, EventScore, Event, Persoon, Comment) {

        $scope.persoonEvent = entity;
        $scope.eventscores = EventScore.query({filter: 'persoonevent-is-null'});
        $q.all([$scope.persoonEvent.$promise, $scope.eventscores.$promise]).then(function() {
            if (!$scope.persoonEvent.eventScore || !$scope.persoonEvent.eventScore.id) {
                return $q.reject();
            }
            return EventScore.get({id : $scope.persoonEvent.eventScore.id}).$promise;
        }).then(function(eventScore) {
            $scope.eventscores.push(eventScore);
        });
        $scope.events = Event.query();
        $scope.persoons = Persoon.query();
        $scope.comments = Comment.query();
        $scope.load = function(id) {
            PersoonEvent.get({id : id}, function(result) {
                $scope.persoonEvent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:persoonEventUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.persoonEvent.id != null) {
                PersoonEvent.update($scope.persoonEvent, onSaveSuccess, onSaveError);
            } else {
                PersoonEvent.save($scope.persoonEvent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
